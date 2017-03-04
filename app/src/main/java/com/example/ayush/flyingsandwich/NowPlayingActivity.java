package com.example.ayush.flyingsandwich;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.ayush.flyingsandwich.Provider.CircleTransform;
import com.squareup.picasso.Picasso;

public class NowPlayingActivity extends BaseActivity {

    ImageView circular_albumart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        initUiComponents();

    }

    private void initUiComponents() {
        circular_albumart = (ImageView) findViewById(R.id.id_np_albumart_circle);
    }

    @Override
    public void onServiceConnectionComplete() {
        Picasso.with(this)
               .load(R.drawable.ic_album_art)
               .transform(new CircleTransform())
               .into(circular_albumart);
    }


}
