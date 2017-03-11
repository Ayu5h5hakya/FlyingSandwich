package com.example.ayush.flyingsandwich.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ayush.flyingsandwich.Fragment.AlbumFragment;
import com.example.ayush.flyingsandwich.Fragment.ArtistFragment;
import com.example.ayush.flyingsandwich.Fragment.SongFragment;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/7/2017.
 */

public class MediaAdapter extends FragmentPagerAdapter {

    private static int FRAGMENT_COUNT=3;
    private ArrayList<PlaylistItem> playlistItems= new ArrayList<>();

    public MediaAdapter(FragmentManager fm, ArrayList<PlaylistItem> playlistItems) {
        super(fm);
        this.playlistItems = playlistItems;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return SongFragment.getInstance(playlistItems);
            case 1: return ArtistFragment.getInstance(playlistItems);
            case 2: return AlbumFragment.getInstance(playlistItems);
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
