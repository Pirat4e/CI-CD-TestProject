package com.example.inikolovski.mvpsampleproject.common;


import android.app.Application;
import android.content.Context;

import com.example.inikolovski.mvpsampleproject.data.DbHelper;
import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbHelper.getInstance().init(this);
        stethoInit(this);
        Timber.plant(new Timber.DebugTree());
    }

    private void stethoInit(Context context) {
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(context);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(context)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(context)
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

}
