package com.example.ayush.flyingsandwich;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.service.PlayerService;

public class NowPlayingActivity extends BaseActivity {

    TextView current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initUiComponents();

    }

    private void initUiComponents() {
        current = (TextView) findViewById(R.id.id_current_song);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //current.setText(playerService.getSelected_song() + "---" + playerService.getSelected_artist());
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
                playerService = localBinder.getService();
                mPlayerBound=true;
                current.setText(playerService.getSelected_song() + "---" + playerService.getSelected_artist());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mPlayerBound = false;
            }
        };

    }
}
