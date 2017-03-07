package com.example.ayush.flyingsandwich;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ayush.flyingsandwich.Interface.PlaybackChangeRequestListener;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Ayush on 2/28/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements PlaybackChangeRequestListener, SongSelectedListener {

    public static String TAG = "witcher";

    protected ServiceConnection mServiceConnection;
    protected boolean mPlayerBound;
    protected MusicDirectoryEngine musicDirectoryEngine;
    protected Realm realm;


    PlayerService playerService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        musicDirectoryEngine = MusicDirectoryEngine.getInstance(this);
        musicDirectoryEngine.storeAllFiles(realm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
                playerService = localBinder.getService();
                mPlayerBound = true;
                onServiceConnectionComplete();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mPlayerBound = false;
            }
        };
        Intent intent = new Intent(this, PlayerService.class);
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

    public abstract void onServiceConnectionComplete();

    @Override
    public void onSongSelected(String song, String artist,String album) {
        PlaylistItem result = realm.where(PlaylistItem.class)
                .equalTo("song_name", song)
                .equalTo("artist_name", artist)
                .findFirst();
        if (result != null) {
            playerService.setSelection(result.getSong_name(), result.getArtist_name(),result.getAlbum_name());
        }

    }

    protected ArrayList<PlaylistItem> getAllMusic() {
        ArrayList<PlaylistItem> finalresults = new ArrayList<>();
        RealmResults<PlaylistItem> results = realm.where(PlaylistItem.class).findAll();
        for (PlaylistItem temp : results) {
            finalresults.add(temp);
        }
        return finalresults;
    }

    protected PlaylistItem getSongByPosition(int index) {
        return musicDirectoryEngine.getSongByPosition(index);
    }
}
