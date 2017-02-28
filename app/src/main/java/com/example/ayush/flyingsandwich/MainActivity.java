package com.example.ayush.flyingsandwich;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Utils.MusicDirectoryEngine;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements SongSelectedListener {

    private RecyclerView mSongRecycleview;

    MusicDirectoryEngine musicDirectoryEngine;
    ArrayList<PlaylistItem> musicFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIComponents();

        mSongRecycleview.setAdapter(new SongAdapter(this,musicFiles));

        musicDirectoryEngine = MusicDirectoryEngine.getInstance(this);
        musicFiles=musicDirectoryEngine.getAllMusic();
    }

    private void initUIComponents() {
    }

    @Override
    public void onSongSelected(int position) {
        playerService.setSelection(musicFiles.get(position).getSong_name(),
                                   musicFiles.get(position).getArtist_name());
    }
}
