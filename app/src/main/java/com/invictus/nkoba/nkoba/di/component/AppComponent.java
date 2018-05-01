package com.invictus.nkoba.nkoba.di.component;

import android.app.Application;

import com.invictus.nkoba.nkoba.KoobaApp;
import com.invictus.nkoba.nkoba.di.module.ActivityBuilder;
import com.invictus.nkoba.nkoba.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by invictus on 2/24/18.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
})
public interface AppComponent extends AndroidInjector<KoobaApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(KoobaApp app);
}
