package com.example.ayush.flyingsandwich;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.GONE;

public class MainActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private RecyclerView mSongRecycleview;
    private View view_selected_song, view_selected_songDetail;
    private SongAdapter mSongAdapter;
    private TextView mSelectedSong, mDetailSongName, mDetailArtistAlbum, mDetailCurrentTime, mDetailRemainingTime;
    private ImageView img_playpause, img_previous, img_next, img_pl_art;
    private ProgressBar mDetailSongProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();

        mSongAdapter = new SongAdapter(this, getAllMusic());
        mSongAdapter.setSongClickListener(this);
        mSongRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mSongRecycleview.setAdapter(mSongAdapter);
        view_selected_song.setOnClickListener(this);
        img_playpause.setOnClickListener(this);
        img_next.setOnClickListener(this);
        img_previous.setOnClickListener(this);
    }

    private void initUIComponents() {
        mSongRecycleview = (RecyclerView) findViewById(R.id.id_playlist);
        view_selected_song = findViewById(R.id.id_selected_song_view);
        mSelectedSong = (TextView) findViewById(R.id.id_v_current_song);
        img_playpause = (ImageView) findViewById(R.id.id_playlist_playpause);
        img_next = (ImageView) findViewById(R.id.id_playlist_fforward);
        img_previous = (ImageView) findViewById(R.id.id_playlist_previous);
        view_selected_songDetail = findViewById(R.id.id_platlist_songdetail);
        mDetailSongProgress = (ProgressBar) findViewById(R.id.id_pl_progress);
        mDetailArtistAlbum = (TextView) findViewById(R.id.id_pl_artistalbum);
        mDetailSongName = (TextView) findViewById(R.id.id_pl_detail_song_name);
        mDetailCurrentTime = (TextView) findViewById(R.id.id_pl_current_time);
        mDetailRemainingTime = (TextView) findViewById(R.id.id_pl_remaining_time);

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
        if (view_selected_song.getVisibility() == GONE && view_selected_songDetail.getVisibility() == GONE) {
            view_selected_songDetail.setVisibility(View.VISIBLE);
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
        view_selected_songDetail.setVisibility(View.VISIBLE);
        mSongAdapter.setSelectedPosition(playerService.getCurrentPosition());
        setCurrentSongDetails(playerService.getSelected_song(), playerService.getSelected_artist(), playerService.getSelected_album());
        mSongAdapter.notifyDataSetChanged();
    }

    private void setCurrentSongDetails(String song, String artist, String album) {
        mSelectedSong.setText(Util.parseMusicFilename(song));
        mDetailSongName.setText(Util.parseMusicFilename(song));
        mDetailArtistAlbum.setText(artist + " - " + album);
        mDetailSongProgress.setMax(playerService.getCurrentTrackMaxDuration());
        Timer progressTimer = new Timer();
        progressTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDetailSongProgress.setProgress(mDetailSongProgress.getProgress() + PlayerService.SEEKER_INTERVAL);
                    }
                });
            }
        }, 0, PlayerService.SEEKER_INTERVAL);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.id_pl_detail_song_name:
                gotoNowPlaying();
                return true;
        }
        return false;
    }
}
