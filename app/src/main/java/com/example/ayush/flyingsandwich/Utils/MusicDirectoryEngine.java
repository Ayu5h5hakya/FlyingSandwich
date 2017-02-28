package com.example.ayush.flyingsandwich.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.ayush.flyingsandwich.Model.PlaylistItem;

import java.util.ArrayList;

/**
 * Created by Ayush on 2/28/2017.
 */

public class MusicDirectoryEngine {

    private ContentResolver mContentResolver;
    private Context mContext;
    private static MusicDirectoryEngine musicDirectoryEngine=null;
    private String MUSIC_DIRECTORY="Music";

    public static MusicDirectoryEngine getInstance(Context context){
        if (musicDirectoryEngine==null){
            musicDirectoryEngine = new MusicDirectoryEngine(context);
        }
        return musicDirectoryEngine;
    }

    private MusicDirectoryEngine(Context context){
        mContext=context;
        mContentResolver = mContext.getContentResolver();
    }

    public ArrayList<PlaylistItem> getAllMusic(){
        ArrayList<PlaylistItem> musicFiles = new ArrayList<>();
        String[] projections = new String[]{
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.ArtistColumns.ARTIST
        };
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                               projections,
                               null,
                               new String[]{"%"+MUSIC_DIRECTORY+"%"},
                               null);
        while (cursor.moveToNext()){
            musicFiles.add(new PlaylistItem(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)),
                                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST))));
        }
        cursor.close();
        return musicFiles;
    }
}
