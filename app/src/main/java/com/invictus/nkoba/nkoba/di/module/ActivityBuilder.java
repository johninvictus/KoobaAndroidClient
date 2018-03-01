package com.invictus.nkoba.nkoba.di.module;

import com.invictus.nkoba.nkoba.ui.activities.AuthActivity;
import com.invictus.nkoba.nkoba.ui.activities.EnterCredentialsActivity;

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
    abstract EnterCredentialsActivity binndEnterCredentialsActivity();
}
