package com.example.ayush.flyingsandwich.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.ayush.flyingsandwich.Interface.PlaybackChangeRequestListener;

import java.io.IOException;

/**
 * Created by Ayush on 2/28/2017.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener{

    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mMediaPlayer;
    private String selected_song, selected_artist,selected_album;
    private int position;

    public int getCurrentPosition() {
        return position;
    }

    public void setCurrentPosition(int position) {
        this.position = position;
    }

    public static int MEDIA_PLAYING = 1;
    public static int MEDIA_PAUSED = 0;
    public static int SEEKER_INTERVAL = 1000;
    public boolean MEDIA_SHUFFLE_ON;
    public boolean MEDIA_REPEAT_ON;

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

    public void setSelection(String selected_song, String selected_artist,String selected_album) {
        this.selected_song = selected_song;
        this.selected_artist = selected_artist;
        this.selected_album = selected_album;
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

    public String getSelected_album() {
        return selected_album;
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

    public int changePlayPauseState() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            return MEDIA_PAUSED;
        } else {
            mMediaPlayer.start();
            return MEDIA_PLAYING;
        }
    }

    public int getCurrentTrackPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getCurrentTrackMaxDuration() {
        return mMediaPlayer.getDuration();
    }

    public boolean isMusicPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void changeShuffleState() {
        MEDIA_SHUFFLE_ON = !MEDIA_SHUFFLE_ON;
    }

    public void changeRepeatState() {
        MEDIA_REPEAT_ON = !MEDIA_REPEAT_ON;
    }
}
