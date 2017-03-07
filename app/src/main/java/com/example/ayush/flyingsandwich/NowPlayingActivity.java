package com.example.ayush.flyingsandwich;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.flyingsandwich.Model.PlaylistItem;
import com.example.ayush.flyingsandwich.Provider.CircleTransform;
import com.example.ayush.flyingsandwich.Provider.CircularSeekBar;
import com.example.ayush.flyingsandwich.service.PlayerService;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class NowPlayingActivity extends BaseActivity implements View.OnClickListener, CircularSeekBar.OnCircularSeekBarChangeListener {

    ImageView circular_albumart, ib_repeat, ib_shuffle, ib_next, ib_previous;
    FloatingActionButton fab_np_playpause;
    TextView tv_currenttime, tv_endtime, tv_currentsong;
    CircularSeekBar sb_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initUiComponents();

        fab_np_playpause.setOnClickListener(this);
        sb_circle.setOnSeekBarChangeListener(this);
        ib_repeat.setOnClickListener(this);
        ib_shuffle.setOnClickListener(this);
        ib_next.setOnClickListener(this);
        ib_previous.setOnClickListener(this);
    }

    private void initUiComponents() {

        circular_albumart = (ImageView) findViewById(R.id.id_np_albumart_circle);
        fab_np_playpause = (FloatingActionButton) findViewById(R.id.id_np_playpause);
        tv_currenttime = (TextView) findViewById(R.id.id_current_time);
        tv_endtime = (TextView) findViewById(R.id.id_end_time);
        sb_circle = (CircularSeekBar) findViewById(R.id.id_circular_seeker);
        tv_currentsong = (TextView) findViewById(R.id.id_current_song);
        ib_repeat = (ImageView) findViewById(R.id.id_np_repeat);
        ib_shuffle = (ImageView) findViewById(R.id.id_np_shuffle);
        ib_next = (ImageView) findViewById(R.id.id_np_next);
        ib_previous = (ImageView) findViewById(R.id.id_np_rewind);
    }

    @Override
    public void onServiceConnectionComplete() {

        sb_circle.setMax(playerService.getCurrentTrackMaxDuration());
        sb_circle.setProgress(playerService.getCurrentTrackPosition());

        String songName = Util.parseMusicFilename(playerService.getSelected_song());

        tv_endtime.setText(Util.convertDurationToMinutes(playerService.getCurrentTrackMaxDuration()));
        tv_currentsong.setText(Util.setSongDisplayTitle(songName, playerService.getSelected_artist()), TextView.BufferType.SPANNABLE);

        Picasso.with(this)
                .load(R.drawable.ic_album_art)
                .transform(new CircleTransform())
                .into(circular_albumart);

        Timer progressTimer = new Timer();
        progressTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sb_circle.setProgress(sb_circle.getProgress() + PlayerService.SEEKER_INTERVAL);
                    }
                });
            }
        }, 0, PlayerService.SEEKER_INTERVAL);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_np_playpause:
                if (playerService.changePlayPauseState() == PlayerService.MEDIA_PLAYING)
                    fab_np_playpause.setImageResource(R.drawable.play_vector);
                else fab_np_playpause.setImageResource(R.drawable.pause_vector);
            case R.id.id_np_shuffle:
                playerService.changeShuffleState();
                break;
            case R.id.id_np_repeat:
                playerService.changeRepeatState();
                break;
            case R.id.id_np_rewind:
                PlaylistItem prevItem = getSongByPosition(playerService.getCurrentPosition() - 1);
                playerService.setCurrentPosition(playerService.getCurrentPosition() - 1);
                onSongSelected(prevItem.getSong_name(), prevItem.getArtist_name(),prevItem.getAlbum_name());
                break;
            case R.id.id_np_next:
                PlaylistItem nextItem = getSongByPosition(playerService.getCurrentPosition() + 1);
                playerService.setCurrentPosition(playerService.getCurrentPosition() + 1);
                onSongSelected(nextItem.getSong_name(), nextItem.getArtist_name(),nextItem.getAlbum_name());
                break;
            default:
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

    @Override
    public void onPlaybackCompleted(int pos) {

    }

    @Override
    public void onSongSelected(String song, String artist,String album) {
        tv_currentsong.setText(Util.setSongDisplayTitle(Util.parseMusicFilename(song), artist), TextView.BufferType.SPANNABLE);
        super.onSongSelected(song, artist,album);
        sb_circle.setProgress(0);
        sb_circle.setMax(playerService.getCurrentTrackMaxDuration());
    }
}