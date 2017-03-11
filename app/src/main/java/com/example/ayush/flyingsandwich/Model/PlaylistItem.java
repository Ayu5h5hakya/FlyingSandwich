package com.example.ayush.flyingsandwich.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by Ayush on 2/28/2017.
 */

public class PlaylistItem extends RealmObject implements Parcelable {
    private String song_name, artist_name, album_name, albumart_url;

    protected PlaylistItem(Parcel in) {
        song_name = in.readString();
        artist_name = in.readString();
        album_name = in.readString();
        albumart_url = in.readString();
    }

    public static final Creator<PlaylistItem> CREATOR = new Creator<PlaylistItem>() {
        @Override
        public PlaylistItem createFromParcel(Parcel in) {
            return new PlaylistItem(in);
        }

        @Override
        public PlaylistItem[] newArray(int size) {
            return new PlaylistItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(song_name);
        parcel.writeString(artist_name);
        parcel.writeString(album_name);
        parcel.writeString(albumart_url);
    }

    public Uri getAlbumart_url() {
        return Uri.parse(albumart_url);
    }

    public void setAlbumart_url(String albumart_url) {
        this.albumart_url = albumart_url;
    }
}
