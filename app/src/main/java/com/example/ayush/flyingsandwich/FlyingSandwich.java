package com.example.ayush.flyingsandwich;

import android.app.Application;
import android.content.Intent;

import com.example.ayush.flyingsandwich.service.PlayerService;

import io.realm.Realm;

/**
 * Created by Ayush on 3/1/2017.
 */

public class FlyingSandwich extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, PlayerService.class);
        startService(intent);
        Realm.init(this);
    }
}
