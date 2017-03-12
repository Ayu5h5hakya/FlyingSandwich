package com.example.ayush.flyingsandwich.Model;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Ayush on 3/11/2017.
 */

public class RealmEngine {

    private static Realm realm;

    public static Realm getInstance(Context context) {
        if (realm == null) {
            Realm.init(context);
            realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public static ArrayList<SongItem> getAllMusic() {
        ArrayList<SongItem> results = new ArrayList<>();
        for (Song item : realm.where(Song.class).findAll()) {
            results.add(new SongItem(item.getName(), "test"));
        }
        return results;
    }

    public static ArrayList<AlbumItem> getAllAlbums() {
        ArrayList<AlbumItem> results = new ArrayList<>();
        for (Album item : realm.where(Album.class).findAll()) {
            results.add(new AlbumItem(item.getmAlbumId()));
        }
        return results;
    }

    public static ArrayList<SongItem> getSongsByAlbumId(Long albumId) {
        ArrayList<SongItem> results = new ArrayList<>();
        for (Song item : realm.where(Song.class).equalTo("mAlbumId", albumId).findAll()) {
            results.add(new SongItem(item.getName(), "test"));
        }
        return results;
    }

    public static String getAlbumName(Long albumId){
        return realm.where(Album.class).equalTo("mAlbumId",albumId).findFirst().getmName();
    }


}
