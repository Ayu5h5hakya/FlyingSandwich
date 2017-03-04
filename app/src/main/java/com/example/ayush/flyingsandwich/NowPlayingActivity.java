package com.example.ayush.flyingsandwich;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Provider.CircleTransform;
import com.example.ayush.flyingsandwich.Provider.CircularSeekBar;
import com.squareup.picasso.Picasso;

public class NowPlayingActivity extends BaseActivity implements View.OnClickListener,CircularSeekBar.OnCircularSeekBarChangeListener{

    ImageView circular_albumart;
    FloatingActionButton fab_np_playpause;
    TextView tv_currenttime,tv_endtime;
    CircularSeekBar sb_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initUiComponents();

        fab_np_playpause.setOnClickListener(this);
        sb_circle.setOnSeekBarChangeListener(this);

    }

    private void initUiComponents() {
        circular_albumart = (ImageView) findViewById(R.id.id_np_albumart_circle);
        fab_np_playpause = (FloatingActionButton) findViewById(R.id.id_np_playpause);
        tv_currenttime = (TextView) findViewById(R.id.id_current_time);
        tv_endtime = (TextView) findViewById(R.id.id_end_time);
        sb_circle = (CircularSeekBar) findViewById(R.id.id_circular_seeker);
    }

    @Override
    public void onServiceConnectionComplete() {
        Picasso.with(this)
               .load(R.drawable.ic_album_art)
               .transform(new CircleTransform())
               .into(circular_albumart);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_np_playpause:
                playerService.changePlayPauseState();
                break;
        }
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
        sb_circle.setProgress(progress);
    }

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {

    }
}
