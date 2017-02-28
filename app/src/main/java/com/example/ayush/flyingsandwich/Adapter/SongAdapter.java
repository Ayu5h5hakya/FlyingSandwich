package com.example.ayush.flyingsandwich.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 2/28/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongsViewHolder>{

    private final Context context;
    private ArrayList<PlaylistItem> musicFiles;
    private SongSelectedListener songSelectedListener;

    public SongAdapter(Context context, ArrayList<PlaylistItem> musicFiles) {
        this.context = context;
        this.musicFiles = musicFiles;
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_selected_song,parent,false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        holder.tv_selected_song.setText(musicFiles.get(position).getSong_name());
        holder.tv_selected_artist.setText(musicFiles.get(position).getArtist_name());

    }

    public void setSongClickListener(SongSelectedListener songSelectedListener){
        this.songSelectedListener = songSelectedListener;
    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_selected_song,tv_selected_artist;
        public SongsViewHolder(View itemView) {
            super(itemView);
            tv_selected_song = (TextView) itemView.findViewById(R.id.id_selected_song);
            tv_selected_artist= (TextView) itemView.findViewById(R.id.id_selected_artist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            songSelectedListener.onSongSelected(getAdapterPosition());
        }
    }
}
