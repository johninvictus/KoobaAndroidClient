package com.invictus.nkoba.nkoba;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by invictus on 1/2/18.
 */

public class Kooba extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else{
            // will add CrashReportingTree
        }
    }
}
