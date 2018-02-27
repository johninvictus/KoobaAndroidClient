package com.invictus.nkoba.nkoba.di.module;

import android.content.SharedPreferences;

import com.invictus.nkoba.nkoba.utils.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by invictus on 2/26/18.
 */
@Module
public class SessionManagerModule {

    @Provides
    @Singleton
    SessionManager providesSessionManager(SharedPreferences preferences) {
        return new SessionManager(preferences);
    }
}
