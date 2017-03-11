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
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/7/2017.
 */

public class SongFragment extends Fragment {

    private static final String ARGUMENT_PLAYLIST = "Playlist";
    private RecyclerView mSongRecycler;
    ArrayList<PlaylistItem> songsList = new ArrayList<>();

    public static SongFragment getInstance(ArrayList<PlaylistItem> playlistItems) {
        SongFragment songFragment = new SongFragment();
        Bundle fragArgs = new Bundle();
        fragArgs.putParcelableArrayList(ARGUMENT_PLAYLIST,playlistItems);
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
        songsList = getArguments().getParcelableArrayList(ARGUMENT_PLAYLIST);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSongRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        SongAdapter songAdapter = new SongAdapter(getActivity(),songsList);
        songAdapter.setSongClickListener((SongSelectedListener) getActivity());
        mSongRecycler.setAdapter(songAdapter);
    }

}
