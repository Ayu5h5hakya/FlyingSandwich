package com.example.ayush.flyingsandwich;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.MediaAdapter;
import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Model.SongItem;
import com.example.ayush.flyingsandwich.service.PlayerService;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private View view_selected_song;
    private SongAdapter mSongAdapter;
    private TextView mSelectedSong;
    private ImageView img_playpause, img_previous, img_next;
    private FloatingActionButton mFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();

        setupListeners();
    }

    private void setupListeners() {
        view_selected_song.setOnClickListener(this);
        img_playpause.setOnClickListener(this);
        img_next.setOnClickListener(this);
        img_previous.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mViewPager.setAdapter(new MediaAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: position" + position + " offset" + positionOffset + " offsetPixels" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
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
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        });
    }

    private void initUIComponents() {
        view_selected_song = findViewById(R.id.id_selected_song_view);
        mSelectedSong = (TextView) findViewById(R.id.id_v_current_song);
        img_playpause = (ImageView) findViewById(R.id.id_playlist_playpause);
        img_next = (ImageView) findViewById(R.id.id_playlist_fforward);
        img_previous = (ImageView) findViewById(R.id.id_playlist_previous);
        mViewPager = (ViewPager) findViewById(R.id.id_main_pager);
        mFab = (FloatingActionButton) findViewById(R.id.id_playerToggle);
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
                SongItem nextItem = getSongByPosition(playerService.getCurrentPosition() + 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() + 1);
                onSongSelected(nextItem.getSong_name(), nextItem.getArtist_name());
                break;
            case R.id.id_playlist_previous:
                SongItem prevItem = getSongByPosition(playerService.getCurrentPosition() - 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() - 1);
                onSongSelected(prevItem.getSong_name(), prevItem.getArtist_name());
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
    public void onSongSelected(String song, String artist) {
        playerService.setCurrentPosition(mSongAdapter.getSelectedPosition());
        mSongAdapter.notifyDataSetChanged();
        setCurrentSongDetails(song, artist);
        super.onSongSelected(song, artist);
    }

    @Override
    public void onPlaybackCompleted(int position) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSongAdapter == null || playerService == null) return;
        mSongAdapter.setSelectedPosition(playerService.getCurrentPosition());
        setCurrentSongDetails(playerService.getSelected_song(), playerService.getSelected_artist());
        mSongAdapter.notifyDataSetChanged();
    }

    private void setCurrentSongDetails(String song, String artist) {
        mSelectedSong.setText(Util.parseMusicFilename(song));
    }

}
