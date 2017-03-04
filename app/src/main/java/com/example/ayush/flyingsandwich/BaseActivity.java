package com.example.ayush.flyingsandwich;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ayush.flyingsandwich.Interface.PlaybackChangeRequestListener;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;

/**
 * Created by Ayush on 2/28/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements PlaybackChangeRequestListener,SongSelectedListener {

    public static String TAG = "witcher";

    protected ServiceConnection mServiceConnection;
    protected boolean mPlayerBound;
    protected ArrayList<PlaylistItem> musicFiles;
    protected MusicDirectoryEngine musicDirectoryEngine;


    PlayerService playerService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicDirectoryEngine = MusicDirectoryEngine.getInstance(this);
        musicFiles = musicDirectoryEngine.getAllMusic();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
                playerService = localBinder.getService();
                mPlayerBound=true;
                onServiceConnectionComplete();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mPlayerBound = false;
            }
        };
        Intent intent = new Intent(this,PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayerBound) {
            unbindService(mServiceConnection);
            mPlayerBound = false;
        }
    }

    private boolean isPlayerRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public abstract void onServiceConnectionComplete();

    @Override
    public void onSongSelected(int position) {
        String song = musicFiles.get(position).getSong_name();
        String artist = musicFiles.get(position).getArtist_name();
        playerService.setSelection(position,song, artist);

    }
}
