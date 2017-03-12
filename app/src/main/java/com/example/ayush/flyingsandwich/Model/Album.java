package com.example.ayush.flyingsandwich.Model;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ayush on 3/11/2017.
 */

public class Album extends RealmObject {

    @PrimaryKey
    private Long mAlbumId;
    private String mName;
    private String mReleaseDate;

    public String getmName() {
        return mName;
    }

    public Album setName(String mName) {
        this.mName = mName;
        return this;
    }

    public Long getmAlbumId() {
        return mAlbumId;
    }

    public Album setAlbumId(Long mAlbumId) {
        this.mAlbumId = mAlbumId;
        return this;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public Album setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
        return this;
    }
}
