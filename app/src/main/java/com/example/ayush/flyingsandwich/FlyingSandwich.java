package com.example.ayush.flyingsandwich;

import android.app.Application;
import android.content.Intent;

import com.example.ayush.flyingsandwich.service.PlayerService;
import com.facebook.stetho.Stetho;

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
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

// Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

// Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

// Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

// Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
    }
}
