package com.example.ayush.flyingsandwich.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.flyingsandwich.Interface.ArtistSelectedListener;
import com.example.ayush.flyingsandwich.Model.ArtistItem;
import com.example.ayush.flyingsandwich.R;

import java.util.ArrayList;

/**
 * Created by Ayush on 3/11/2017.
 */

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    private final Context context;
    private final ArrayList<ArtistItem> artistItems;
    private ArtistSelectedListener artistSelectedListener;

    public ArtistAdapter(Context context, ArrayList<ArtistItem> artistItems){
        this.context = context;
        this.artistItems = artistItems;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_artistlist,parent,false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.artistname.setText(artistItems.get(position).getName());
    }

    public void setArtistSelectedListener(ArtistSelectedListener artistSelectedListener){
        this.artistSelectedListener = artistSelectedListener;
    }

    @Override
    public int getItemCount() {
        return artistItems.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private FrameLayout artistFrame;
        private TextView artistname;
        public ArtistViewHolder(View itemView) {
            super(itemView);
            artistFrame = (FrameLayout) itemView.findViewById(R.id.id_artist_frame);
            artistname = (TextView) itemView.findViewById(R.id.id_sv_artist_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            artistSelectedListener.artistSelected(getAdapterPosition());
        }
    }
}
