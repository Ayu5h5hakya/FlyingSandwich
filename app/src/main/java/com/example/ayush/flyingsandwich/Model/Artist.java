package com.example.ayush.flyingsandwich.Model;

import io.realm.RealmObject;

/**
 * Created by Ayush on 3/11/2017.
 */

public class Artist extends RealmObject {

    private String mName;
    private Long mArtistId;

    public String getmName() {
        return mName;
    }

    public Artist setName(String mName) {
        this.mName = mName;
        return this;
    }

    public Long getmArtistId() {
        return mArtistId;
    }

    public Artist setArtistId(Long mArtistId) {
        this.mArtistId = mArtistId;
        return this;
    }
}
