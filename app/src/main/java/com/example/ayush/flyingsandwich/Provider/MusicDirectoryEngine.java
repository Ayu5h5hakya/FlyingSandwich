package com.example.ayush.flyingsandwich.Provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.ayush.flyingsandwich.Model.Album;
import com.example.ayush.flyingsandwich.Model.Artist;
import com.example.ayush.flyingsandwich.Model.SongItem;
import com.example.ayush.flyingsandwich.Model.Song;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by Ayush on 2/28/2017.
 */

public class MusicDirectoryEngine {

    private ContentResolver mContentResolver;
    private Context mContext;
    private static MusicDirectoryEngine musicDirectoryEngine = null;
    private String MUSIC_DIRECTORY = "Music";
    private ArrayList<SongItem> musicFiles = new ArrayList<>();
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

//    public void storeAllFiles(Realm realm) {
//        if (realm.where(SongItem.class).findAll().size() == 0) {
//            String[] projections = new String[]{
//                    MediaStore.Audio.AudioColumns.DATA,
//                    MediaStore.Audio.ArtistColumns.ARTIST,
//                    MediaStore.Audio.AlbumColumns.ALBUM,
//                    MediaStore.Audio.AlbumColumns.ALBUM_ID
//            };
//            final Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    projections,
//                    MediaStore.Audio.Media.DATA + " like ? ",
//                    new String[]{"%" + MUSIC_DIRECTORY + "%"},
//                    null);
//            while (cursor.moveToNext()) {
//
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        SongItem item = realm.createObject(SongItem.class);
//                        item.setSong_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)));
//                        item.setArtist_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST)));
//                        item.setAlbum_name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM)));
//                        String albumUri = ContentUris.withAppendedId(sArtworkUri,
//                                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID))).toString();
//                        item.setAlbumart_url(albumUri);
//                        musicFiles.add(item);
//                    }
//                });
//
//            }
//            cursor.close();
//        } else {
//            RealmResults<SongItem> results = realm.where(SongItem.class).findAll();
//            for (SongItem temp : results) {
//                musicFiles.add(temp);
//            }
//        }
//    }

    public void setupRealm(Realm realm) {
        if (realm.where(Song.class).findAll().size() == 0) {
            String[] projections = new String[]{
                    MediaStore.Audio.AudioColumns.DATA,
                    MediaStore.Audio.ArtistColumns.ARTIST,
                    MediaStore.Audio.AlbumColumns.ALBUM,
                    MediaStore.Audio.AlbumColumns.ALBUM_ID,
                    MediaStore.Audio.Artists._ID
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

                        Song song = realm.createObject(Song.class);
                        song.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)))
                                .setAlbumId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID)))
                                .setArtistId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)))
                                .setisFavourite(false);

                        Artist artist = realm.createObject(Artist.class);
                        artist.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST)))
                                .setArtistId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)));

                        Album album = new Album();
                        album.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM)))
                             .setAlbumId(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ID)));
                        realm.insertOrUpdate(album);
                    }
                });

            }
            cursor.close();

        }
    }

    public SongItem getSongByPosition(int index) {
        return musicFiles.get(index);
    }

}
