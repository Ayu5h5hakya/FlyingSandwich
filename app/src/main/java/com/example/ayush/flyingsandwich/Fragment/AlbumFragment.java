package com.example.ayush.flyingsandwich.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Adapter.AlbumAdapter;
import com.example.ayush.flyingsandwich.Adapter.SongAdapter;
import com.example.ayush.flyingsandwich.Interface.AlbumSelectedListener;
import com.example.ayush.flyingsandwich.Model.AlbumItem;
import com.example.ayush.flyingsandwich.Model.RealmEngine;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Ayush on 3/8/2017.
 */

public class AlbumFragment extends Fragment implements AlbumSelectedListener {

    private RecyclerView mAlbumRecycler, mAlbumSongs;
    private TextView tv_Albumname,tv_Albumartist,iv_emptylist;
    ArrayList<AlbumItem> albumList = new ArrayList<>();
    private SongAdapter mSongAdapter;

    public static AlbumFragment getInstance() {
        AlbumFragment albumFragment = new AlbumFragment();
        return albumFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albumlist, container, false);
        mAlbumRecycler = (RecyclerView) view.findViewById(R.id.id_av_albums);
        mAlbumSongs = (RecyclerView) view.findViewById(R.id.id_av_albumsongs);
        tv_Albumname = (TextView) view.findViewById(R.id.id_av_album);
        tv_Albumartist = (TextView) view.findViewById(R.id.id_av_artistname);
        iv_emptylist = (TextView) view.findViewById(R.id.id_av_emptyview);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumList = RealmEngine.getAllAlbums();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager albumArtlinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        albumArtlinearLayoutManager.setReverseLayout(false);
        mAlbumRecycler.setLayoutManager(albumArtlinearLayoutManager);

        LinearLayoutManager albumSongslinearLayoutManager = new LinearLayoutManager(getActivity());
        mAlbumSongs.setLayoutManager(albumSongslinearLayoutManager);

        AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), albumList);
        albumAdapter.setSongClickListener(this);
        mAlbumRecycler.setAdapter(albumAdapter);
    }

    @Override
    public void onAlbumSelected(Long albumId) {
        if (mAlbumSongs.getVisibility() == GONE) {
            mAlbumSongs.setVisibility(View.VISIBLE);
            iv_emptylist.setVisibility(GONE);
        }
        tv_Albumname.setText(RealmEngine.getAlbumName(albumId));
        mSongAdapter = new SongAdapter(getActivity(), RealmEngine.getSongsByAlbumId(albumId));
        mAlbumSongs.setAdapter(mSongAdapter);
    }
}
