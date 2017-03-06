package com.example.ayush.flyingsandwich;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mSongRecycleview;
    private View view_selected_song;
    private SongAdapter mSongAdapter;
    private TextView mSelectedSong;
    private ImageView img_playpause, img_previous, img_next;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_selected_song_view:
                Intent intent = new Intent(this, NowPlayingActivity.class);
                startActivity(intent);
                break;
            case R.id.id_playlist_playpause:
                if (playerService.changePlayPauseState() == PlayerService.MEDIA_PLAYING)
                    img_playpause.setImageResource(R.drawable.play_vector);
                else img_playpause.setImageResource(R.drawable.pause_vector);
                break;
            case R.id.id_playlist_fforward:
                PlaylistItem nextItem = getSongByPosition(playerService.getCurrentPosition() + 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() + 1);
                onSongSelected(nextItem.getSong_name(), nextItem.getArtist_name());
                break;
            case R.id.id_playlist_previous:
                PlaylistItem prevItem = getSongByPosition(playerService.getCurrentPosition() - 1);
                mSongAdapter.setSelectedPosition(playerService.getCurrentPosition() - 1);
                onSongSelected(prevItem.getSong_name(), prevItem.getArtist_name());
                break;
            default:
                break;
        }
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
        if (view_selected_song.getVisibility() == View.GONE)
            view_selected_song.setVisibility(View.VISIBLE);
        mSelectedSong.setText(Util.parseMusicFilename(song));
        super.onSongSelected(song, artist);
    }

    @Override
    public void onPlaybackCompleted(int position) {

    }
}
