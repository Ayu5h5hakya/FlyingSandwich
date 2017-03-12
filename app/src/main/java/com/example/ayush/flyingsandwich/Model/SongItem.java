package com.example.ayush.flyingsandwich.Model;

/**
 * Created by Ayush on 2/28/2017.
 */

public class SongItem {
    private String song_name, artist_name;

    public SongItem(String song_name, String artist_name) {
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
