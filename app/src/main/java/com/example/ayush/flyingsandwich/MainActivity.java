package com.example.ayush.flyingsandwich;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.MediaAdapter;
import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private ViewPager mViewPager;
    private View view_selected_song, view_selected_songDetail;
    private SongAdapter mSongAdapter;
    private TextView mSelectedSong, mDetailSongName, mDetailArtistAlbum, mDetailCurrentTime, mDetailRemainingTime;
    private ImageView img_playpause, img_previous, img_next, img_pl_art,img_pl_song,img_pl_artist,img_pl_album,img_child_overflow;
    private ProgressBar mDetailSongProgress;
    private FloatingActionButton mFab;

    public static String ARGUMENTS_PLAYLIST="Playlist";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();

        setupListeners();
    }

    private void setupListeners() {
        mSongAdapter = new SongAdapter(this, getAllMusic());
        mSongAdapter.setSongClickListener(this);
        view_selected_song.setOnClickListener(this);
        img_playpause.setOnClickListener(this);
        img_next.setOnClickListener(this);
        img_previous.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mViewPager.setAdapter(new MediaAdapter(getSupportFragmentManager(),getAllMusic()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: position"+position+" offset" + positionOffset + " offsetPixels"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mFab.setImageResource(R.drawable.song_drawable);
                        break;
                    case 1:
                        mFab.setImageResource(R.drawable.artist_drawable);
                        break;
                    case 2:
                        mFab.setImageResource(R.drawable.album_drawable);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: "+state);
            }
        });
    }

    private void initUIComponents() {
        view_selected_song = findViewById(R.id.id_selected_song_view);
        mSelectedSong = (TextView) findViewById(R.id.id_v_current_song);
        img_playpause = (ImageView) findViewById(R.id.id_playlist_playpause);
        img_next = (ImageView) findViewById(R.id.id_playlist_fforward);
        img_previous = (ImageView) findViewById(R.id.id_playlist_previous);
        //view_selected_songDetail = findViewById(R.id.id_platlist_songdetail);
//        mDetailSongProgress = (ProgressBar) findViewById(R.id.id_pl_progress);
//        mDetailArtistAlbum = (TextView) findViewById(R.id.id_pl_artistalbum);
//        mDetailSongName = (TextView) findViewById(R.id.id_pl_detail_song_name);
//        mDetailCurrentTime = (TextView) findViewById(R.id.id_pl_current_time);
//        mDetailRemainingTime = (TextView) findViewById(R.id.id_pl_remaining_time);
        img_pl_song = (ImageView) findViewById(R.id.id_pl_song);
        img_pl_artist = (ImageView) findViewById(R.id.id_pl_artist);
        img_pl_album = (ImageView) findViewById(R.id.id_pl_album);
        mViewPager = (ViewPager) findViewById(R.id.id_main_pager);
        mFab = (FloatingActionButton) findViewById(R.id.id_playerToggle);
        img_child_overflow = (ImageView) findViewById(R.id.id_child_pl_menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_selected_song_view:
                gotoNowPlaying();
                break;
            case R.id.id_playlist_playpause:
                if (playerService.changePlayPauseState() == PlayerService.MEDIA_PLAYING)
                    img_playpause.setImageResource(R.drawable.play_vector);
                else img_playpause.setImageResource(R.drawable.pause_vector);
                break;
            case R.id.id_playlist_fforward:
                PlaylistItem nextItem = getSongByPosition(playerService.getCurrentPosition() + 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() + 1);
                onSongSelected(nextItem.getSong_name(), nextItem.getArtist_name(), nextItem.getAlbum_name());
                break;
            case R.id.id_playlist_previous:
                PlaylistItem prevItem = getSongByPosition(playerService.getCurrentPosition() - 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() - 1);
                onSongSelected(prevItem.getSong_name(), prevItem.getArtist_name(), prevItem.getAlbum_name());
                break;
            case R.id.id_pl_album:
                break;
            case R.id.id_pl_artist:
                break;
            case R.id.id_pl_song:
                break;
            case R.id.id_child_pl_menu:

                break;
            default:
                break;
        }
    }

    private void gotoNowPlaying() {
        Intent intent = new Intent(this, NowPlayingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onServiceConnectionComplete() {

    }

    @Override
    public void onSongSelected(String song, String artist, String album) {
        playerService.setCurrentPosition(mSongAdapter.getSelectedPosition());
        mSongAdapter.notifyDataSetChanged();
        if (view_selected_song.getVisibility() == GONE) {
            view_selected_song.setVisibility(View.VISIBLE);
        }
        setCurrentSongDetails(song, artist, album);
        super.onSongSelected(song, artist, album);
    }

    @Override
    public void onPlaybackCompleted(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSongAdapter == null || playerService == null) return;
        mSongAdapter.setSelectedPosition(playerService.getCurrentPosition());
        setCurrentSongDetails(playerService.getSelected_song(), playerService.getSelected_artist(), playerService.getSelected_album());
        mSongAdapter.notifyDataSetChanged();
    }

    private void setCurrentSongDetails(String song, String artist, String album) {
        mSelectedSong.setText(Util.parseMusicFilename(song));
    }

}
