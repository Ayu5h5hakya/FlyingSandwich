package com.example.ayush.flyingsandwich.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ayush.flyingsandwich.Interface.AlbumSelectedListener;
import com.example.ayush.flyingsandwich.Model.AlbumItem;
import com.example.ayush.flyingsandwich.Model.SongItem;
import com.example.ayush.flyingsandwich.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/11/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {


    private final Context mcontext;
    private final ArrayList<AlbumItem> albumfiles;
    private AlbumSelectedListener albumSelectedListener;

    public AlbumAdapter(Context mcontext, ArrayList<AlbumItem> albumfiles) {
        this.mcontext = mcontext;
        this.albumfiles = albumfiles;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.child_albumadapter,parent,false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Picasso.with(mcontext).load(albumfiles.get(position).getAlbumart()).into(holder.malbumart);
    }

    @Override
    public int getItemCount() {
        return albumfiles.size();
    }

    public void setSongClickListener(AlbumSelectedListener albumSelectedListener) {
        this.albumSelectedListener = albumSelectedListener;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView malbumart;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            malbumart = (ImageView) itemView.findViewById(R.id.id_av_art);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            albumSelectedListener.onAlbumSelected(albumfiles.get(getAdapterPosition()).getmAlbumId());
        }
    }
}
