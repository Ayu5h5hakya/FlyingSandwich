package com.example.ayush.flyingsandwich.Model;

import io.realm.RealmObject;

/**
 * Created by Ayush on 3/11/2017.
 */

public class Song extends RealmObject {

    private String mName;
    private Long mArtistId,mAlbumId;
    private boolean misFavourite;

    public String getName() {
        return mName;
    }

    public Song setName(String mName) {
        this.mName = mName;
        return this;
    }

    public Long getArtistId() {
        return mArtistId;
    }

    public Song setArtistId(Long mArtistId) {
        this.mArtistId = mArtistId;
        return this;
    }

    public Long getAlbumId() {
        return mAlbumId;
    }

    public Song setAlbumId(Long mAlbumId) {
        this.mAlbumId = mAlbumId;
        return this;
    }

    public boolean isisFavourite() {
        return misFavourite;
    }

    public Song setisFavourite(boolean misFavourite) {
        this.misFavourite = misFavourite;
        return this;
    }
}
