package com.example.ayush.flyingsandwich.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.flyingsandwich.Adapter.AlbumAdapter;
import com.example.ayush.flyingsandwich.Interface.AlbumSelectedListener;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/8/2017.
 */

public class AlbumFragment extends Fragment {

    private RecyclerView mAlbumRecycler,mAlbumSongs;
    private static final String ARGUMENT_PLAYLIST = "Playlist";
    ArrayList<PlaylistItem> albumList = new ArrayList<>();

    public static AlbumFragment getInstance(ArrayList<PlaylistItem> albumItems) {
        AlbumFragment albumFragment = new AlbumFragment();
        Bundle fragArgs = new Bundle();
        fragArgs.putParcelableArrayList(ARGUMENT_PLAYLIST,albumItems);
        albumFragment.setArguments(fragArgs);
        return albumFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albumlist, container, false);
        mAlbumRecycler = (RecyclerView) view.findViewById(R.id.id_av_albums);
        mAlbumSongs = (RecyclerView) view.findViewById(R.id.id_av_albumsongs);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumList = getArguments().getParcelableArrayList(ARGUMENT_PLAYLIST);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true);
        linearLayoutManager.setReverseLayout(false);
        mAlbumRecycler.setLayoutManager(linearLayoutManager);
        AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(),albumList);
        //albumAdapter.setSongClickListener((AlbumSelectedListener) getActivity());
        mAlbumRecycler.setAdapter(albumAdapter);
    }
}
