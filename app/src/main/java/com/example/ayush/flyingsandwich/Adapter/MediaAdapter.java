package com.example.ayush.flyingsandwich.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.flyingsandwich.R;

/**
 * Created by Ayush on 3/7/2017.
 */

public class MediaAdapter extends PagerAdapter{

    private int NUMBER_OF_FRAGS = 3;
    Context context;

    public MediaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup layout = null;
        switch (position){
            case 0:
                layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_playlist,container,false);
                break;
            case 1:
                layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_artistlist,container,false);
                break;
            case 2:
                layout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_albumlist,container,false);
                break;
            default:
                break;
        }
        return layout;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
