package com.invictus.nkoba.nkoba.di.module;

import com.invictus.nkoba.nkoba.api.KoobaServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by invictus on 2/24/18.
 */

@Module(includes = {
        ViewModelModule.class,
        NetworkModule.class,
        StorageModule.class,
        SessionManagerModule.class
})
public class AppModule {
    /***
     * Expose something
     * Retrofit maybe
     ***/

    @Provides
    @Singleton
    KoobaServerApi providesKoobaServerApi(Retrofit retrofit) {
        return retrofit.create(KoobaServerApi.class);
    }

}
