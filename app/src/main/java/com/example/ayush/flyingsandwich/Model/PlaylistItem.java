package com.example.ayush.flyingsandwich.Model;

import io.realm.RealmObject;

/**
 * Created by Ayush on 2/28/2017.
 */

public class PlaylistItem extends RealmObject{
    private String song_name,artist_name,album_name;

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public PlaylistItem() {

    }

    public PlaylistItem(String song_name, String artist_name) {
        this.song_name = song_name;
        this.artist_name = artist_name;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }
}
