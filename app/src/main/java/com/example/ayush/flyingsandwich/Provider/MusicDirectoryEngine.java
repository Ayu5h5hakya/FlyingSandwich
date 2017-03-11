package com.example.ayush.flyingsandwich.Provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.ayush.flyingsandwich.Model.PlaylistItem;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ayush on 2/28/2017.
 */

public class MusicDirectoryEngine {

    private ContentResolver mContentResolver;
    private final static Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    private Context mContext;
    private static MusicDirectoryEngine musicDirectoryEngine = null;
    private String MUSIC_DIRECTORY = "Music";
    private ArrayList<PlaylistItem> musicFiles = new ArrayList<>();
    public static String FORMAT = ".mp3";

    public static MusicDirectoryEngine getInstance(Context context) {
        if (musicDirectoryEngine == null) {
            musicDirectoryEngine = new MusicDirectoryEngine(context);
        }
        return musicDirectoryEngine;
    }

    private MusicDirectoryEngine(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public void storeAllFiles(Realm realm) {
        if (realm.where(PlaylistItem.class).findAll().size() == 0) {
            String[] projections = new String[]{
                    MediaStore.Audio.AudioColumns.DATA,
                    MediaStore.Audio.ArtistColumns.ARTIST,
                    MediaStore.Audio.AlbumColumns.ALBUM,
                    MediaStore.Audio.AlbumColumns.ALBUM_ID
            };
            final Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projections,
                    MediaStore.Audio.Media.DATA + " like ? ",
                    new String[]{"%" + MUSIC_DIRECTORY + "%"},
                    null);
            while (cursor.moveToNext()) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        PlaylistItem item = realm.createObject(PlaylistItem.class);
                        item.setSong_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)));
                        item.setArtist_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST)));
                        item.setAlbum_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM)));
                        String albumUri = ContentUris.withAppendedId(sArtworkUri,
                                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID))).toString();
                        item.setAlbumart_url(albumUri);
                        musicFiles.add(item);
                    }
                });

            }
            cursor.close();
        } else {
            RealmResults<PlaylistItem> results = realm.where(PlaylistItem.class).findAll();
            for (PlaylistItem temp : results) {
                musicFiles.add(temp);
            }
        }


    }

    public PlaylistItem getSongByPosition(int index) {
        return musicFiles.get(index);
    }

}
