package com.example.ayush.flyingsandwich.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.SongItem;
import com.example.ayush.flyingsandwich.Model.RealmEngine;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/7/2017.
 */

public class SongFragment extends Fragment implements SongSelectedListener{

    private RecyclerView mSongRecycler;
    private SongAdapter mSongAdapter;
    ArrayList<SongItem> songsList = new ArrayList<>();

    public static SongFragment getInstance() {
        SongFragment songFragment = new SongFragment();
        Bundle fragArgs = new Bundle();
        songFragment.setArguments(fragArgs);
        return songFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        mSongRecycler = (RecyclerView) view.findViewById(R.id.id_playlist);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songsList = RealmEngine.getAllMusic();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSongRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSongAdapter = new SongAdapter(getActivity(), songsList);
        mSongAdapter.setSongClickListener(this);
        mSongRecycler.setAdapter(mSongAdapter);
    }

    @Override
    public void onSongSelected(String song, String artist) {
        mSongAdapter.notifyDataSetChanged();
    }
}
