package com.example.ayush.flyingsandwich;

import android.os.Bundle;
import android.widget.TextView;

public class NowPlayingActivity extends BaseActivity {

    TextView current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initUiComponents();

        current.setText(playerService.getSelected_song() + "---" + playerService.getSelected_artist());
    }

    private void initUiComponents() {
        current = (TextView) findViewById(R.id.id_current_song);
    }
}
