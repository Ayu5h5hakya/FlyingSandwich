package com.example.ayush.flyingsandwich.Model;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by Ayush on 3/12/2017.
 */

public class AlbumItem {

    private Long mAlbumId;
    private Uri albumart;
    private final static Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");


    public AlbumItem(Long mAlbumId) {
        this.mAlbumId = mAlbumId;
        albumart = ContentUris.withAppendedId(sArtworkUri,this.mAlbumId);
    }

    public Uri getAlbumart() {
        return albumart;
    }

    public Long getmAlbumId() {
        return mAlbumId;
    }
}
