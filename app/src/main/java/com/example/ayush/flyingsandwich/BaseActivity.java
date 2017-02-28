package com.example.ayush.flyingsandwich;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.example.ayush.flyingsandwich.service.PlayerService;

/**
 * Created by Ayush on 2/28/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private boolean mPlayerBound;

    public static String TAG = "witcher";

    PlayerService playerService;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
        bindService(intent, mServiceConnection, isPlayerRunning(PlayerService.class) ? 0 : Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayerBound) {
            unbindService(mServiceConnection);
            mPlayerBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
            playerService = localBinder.getService();
            mPlayerBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mPlayerBound = false;
        }
    };

    private boolean isPlayerRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
