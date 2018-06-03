package com.invictus.nkoba.nkoba;

import android.content.pm.ActivityInfo;
import android.util.Log;

import com.invictus.nkoba.nkoba.di.component.AppComponent;
import com.invictus.nkoba.nkoba.di.component.DaggerAppComponent;
import com.invictus.nkoba.nkoba.utils.NetworkUtils;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

/**
 * Created by invictus on 1/2/18.
 */

public class KoobaApp extends DaggerApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // will add CrashReportingTree
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        appComponent.inject(this);
        return appComponent;
    }
}
