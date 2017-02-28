package com.example.ayush.flyingsandwich.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayush.flyingsandwich.Model.PlaylistItem;

import java.util.ArrayList;

/**
 * Created by Ayush on 2/28/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongsViewHolder>{

    private final Context context;
    private ArrayList<PlaylistItem> musicFiles;

    public SongAdapter(Context context, ArrayList<PlaylistItem> musicFiles) {
        this.context = context;
        this.musicFiles = musicFiles;
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder{

        public SongsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
