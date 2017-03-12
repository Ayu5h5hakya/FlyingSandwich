package com.example.ayush.flyingsandwich;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ayush.flyingsandwich.Interface.PlaybackChangeRequestListener;
import com.example.ayush.flyingsandwich.Interface.SongSelectedListener;
import com.example.ayush.flyingsandwich.Model.SongItem;
import com.example.ayush.flyingsandwich.Model.RealmEngine;
import com.example.ayush.flyingsandwich.Provider.MusicDirectoryEngine;
import com.example.ayush.flyingsandwich.service.PlayerService;

import io.realm.Realm;

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
        musicDirectoryEngine = MusicDirectoryEngine.getInstance(this);
        realm = RealmEngine.getInstance(this);
        musicDirectoryEngine.setupRealm(realm);
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
    public void onSongSelected(String song, String artist) {
//        SongItem result = realm.where(SongItem.class)
//                .equalTo("song_name", song)
//                .equalTo("artist_name", artist)
//                .findFirst();
//        if (result != null) {
//            playerService.setSelection(result.getSong_name(), result.getArtist_name(),result.getAlbum_name(),result.getAlbumart_url());
//        }

    }

//    protected ArrayList<SongItem> getAllMusic() {
//        ArrayList<SongItem> finalresults = new ArrayList<>();
//        RealmResults<SongItem> results = realm.where(SongItem.class).findAll();
//        for (SongItem temp : results) {
//            finalresults.add(temp);
//        }
//        return finalresults;
//    }

    protected SongItem getSongByPosition(int index) {
        return musicDirectoryEngine.getSongByPosition(index);
    }
}
