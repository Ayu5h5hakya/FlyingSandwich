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

    public static String TAG = "witcher";

    protected ServiceConnection mServiceConnection;
    protected boolean mPlayerBound;

    PlayerService playerService;

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intent, mServiceConnection,Context.BIND_AUTO_CREATE);
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
}
