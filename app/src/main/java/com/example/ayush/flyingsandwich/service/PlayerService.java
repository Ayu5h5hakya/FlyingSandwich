package com.example.ayush.flyingsandwich.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.example.ayush.flyingsandwich.Provider.CircularSeekBar;

import java.io.IOException;

/**
 * Created by Ayush on 2/28/2017.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mMediaPlayer;
    private String selected_song, selected_artist;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
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

    public void setSelection(String selected_song, String selected_artist) {
        this.selected_song = selected_song;
        this.selected_artist = selected_artist;
        playCurrentSelection();
    }


    private void playCurrentSelection() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
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

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
    }

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    public void changePlayPauseState() {
        if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
        else mMediaPlayer.start();
    }

    public void attachSeeker(CircularSeekBar seeker){
        seeker.setProgress(mMediaPlayer.getCurrentPosition());
    }

    public void attachSeeker(SeekBar seeker){

    }

    public int getCurrentTrackMaxDuration(){return mMediaPlayer.getDuration();}
}
