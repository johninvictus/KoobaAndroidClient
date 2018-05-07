package com.invictus.nkoba.nkoba.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.invictus.nkoba.nkoba.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by invictus on 2/26/18.
 */

@Module
public class StorageModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(
                application.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }
}