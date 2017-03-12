package com.example.ayush.flyingsandwich.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ayush.flyingsandwich.Fragment.AlbumFragment;
import com.example.ayush.flyingsandwich.Fragment.ArtistFragment;
import com.example.ayush.flyingsandwich.Fragment.SongFragment;

/**
 * Created by Ayush on 3/7/2017.
 */

public class MediaAdapter extends FragmentPagerAdapter {

    private static int FRAGMENT_COUNT=3;

    public MediaAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return SongFragment.getInstance();
            case 1: return ArtistFragment.getInstance();
            case 2: return AlbumFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
