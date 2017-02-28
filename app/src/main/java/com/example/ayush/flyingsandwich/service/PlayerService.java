package com.example.ayush.flyingsandwich.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * Created by Ayush on 2/28/2017.
 */

public class PlayerService extends Service {

    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mMediaPlayer;
    private String selected_song,selected_artist;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    public void setSelection(String selected_song,String selected_artist){
        this.selected_song=selected_song;
        this.selected_artist=selected_artist;
    }

    public void playCurrentSelection(){
        try {
            mMediaPlayer.setDataSource(selected_song);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSelected_song() {
        return selected_song;
    }

    public String getSelected_artist() {
        return selected_artist;
    }

    public class LocalBinder extends Binder{
        public PlayerService getService(){
            return PlayerService.this;
        }
    }
}
