package com.example.ayush.flyingsandwich;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Utils.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements SongSelectedListener,View.OnClickListener {

    private RecyclerView mSongRecycleview;
    private View view_selected_song;
    private SongAdapter mSongAdapter;
    private TextView mSelectedSong, mSelectedArtist;

    MusicDirectoryEngine musicDirectoryEngine;
    ArrayList<PlaylistItem> musicFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();

        musicDirectoryEngine = MusicDirectoryEngine.getInstance(this);
        musicFiles = musicDirectoryEngine.getAllMusic();
        mSongAdapter = new SongAdapter(this, musicFiles);
        mSongAdapter.setSongClickListener(this);
        mSongRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mSongRecycleview.setAdapter(mSongAdapter);
        view_selected_song.setOnClickListener(this);
    }

    private void initUIComponents() {
        mSongRecycleview = (RecyclerView) findViewById(R.id.id_playlist);
        view_selected_song = findViewById(R.id.id_selected_song_view);
        mSelectedSong = (TextView) findViewById(R.id.id_selected_song);
        mSelectedArtist = (TextView) findViewById(R.id.id_selected_artist);
    }

    @Override
    public void onSongSelected(int position) {
        String song = musicFiles.get(position).getSong_name();
        String artist = musicFiles.get(position).getArtist_name();
        mSelectedArtist.setText(artist);
        mSelectedSong.setText(song);
        playerService.setSelection(song, artist);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_selected_song_view:
                Intent intent = new Intent(this,NowPlayingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
                playerService = localBinder.getService();
                mPlayerBound=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mPlayerBound = false;
            }
        };
    }
}
