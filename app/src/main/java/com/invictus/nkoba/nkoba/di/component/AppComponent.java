package com.invictus.nkoba.nkoba.di.component;

import android.app.Application;

import com.invictus.nkoba.nkoba.KoobaApp;
import com.invictus.nkoba.nkoba.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by invictus on 2/24/18.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(KoobaApp app);
}
