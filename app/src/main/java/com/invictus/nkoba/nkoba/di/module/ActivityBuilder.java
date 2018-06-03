package com.invictus.nkoba.nkoba.di.module;

import com.invictus.nkoba.nkoba.di.module.fragment_module.MainFragmentModule;
import com.invictus.nkoba.nkoba.ui.SplashActivity;
import com.invictus.nkoba.nkoba.ui.activities.AuthActivity;
import com.invictus.nkoba.nkoba.ui.activities.EnterCredentialsActivity;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;
import com.invictus.nkoba.nkoba.ui.activities.NotificationActivity;
import com.invictus.nkoba.nkoba.ui.activities.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by invictus on 2/26/18.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract AuthActivity bindAuthActivity();

    @ContributesAndroidInjector
    abstract EnterCredentialsActivity bindEnterCredentialsActivity();

    @ContributesAndroidInjector
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = MainFragmentModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract NotificationActivity bindNotificationActivity();
}
