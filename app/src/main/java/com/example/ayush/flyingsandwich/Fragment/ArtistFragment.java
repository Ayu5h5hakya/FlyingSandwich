package com.example.ayush.flyingsandwich.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.flyingsandwich.Adapter.ArtistAdapter;
import com.example.ayush.flyingsandwich.Interface.ArtistSelectedListener;
import com.example.ayush.flyingsandwich.Model.ArtistItem;
import com.example.ayush.flyingsandwich.Model.RealmEngine;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/8/2017.
 */

public class ArtistFragment extends Fragment implements ArtistSelectedListener{

    private RecyclerView mArtistRecycler;
    private ArrayList<ArtistItem> artistItems = new ArrayList<>();

    public static ArtistFragment getInstance() {
        return new ArtistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artistlist, container, false);
        mArtistRecycler = (RecyclerView) view.findViewById(R.id.id_sv_recycler);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artistItems = RealmEngine.getAllArtists();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        mArtistRecycler.setLayoutManager(gridLayoutManager);
        ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(),artistItems);
        artistAdapter.setArtistSelectedListener(this);
        mArtistRecycler.setAdapter(artistAdapter);

    }

    @Override
    public void artistSelected(int position) {
        mArtistRecycler.smoothScrollToPosition(position);
    }
}
