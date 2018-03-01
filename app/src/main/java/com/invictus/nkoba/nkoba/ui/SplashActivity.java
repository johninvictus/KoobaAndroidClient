package com.invictus.nkoba.nkoba.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.invictus.nkoba.nkoba.ui.activities.EnterCredentialsActivity;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;
import com.invictus.nkoba.nkoba.ui.activities.OnboardingActivity;
import com.invictus.nkoba.nkoba.ui.activities.WelcomeActivity;
import com.invictus.nkoba.nkoba.utils.ScreenUtils;
import com.invictus.nkoba.nkoba.utils.SessionManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by invictus on 1/3/18.
 */

public class SplashActivity extends AppCompatActivity implements HasActivityInjector {

    DispatchingAndroidInjector<Activity> injector;


    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        //make status bar transparent
        ScreenUtils.changeStatusBarColor(this);
        ScreenUtils.makeFullScreen(this);

        new Handler().postDelayed(() -> {
            if (!sessionManager.wasOnBoardingShown()) {
                sessionManager.setOnBoardingShown(true);
                OnboardingActivity.startActivity(this);
            } else if (sessionManager.isLoggedIn() && sessionManager.wasOnBoardingShown()) {
                if (sessionManager.getIsDetailsProvided()) {
                    MainActivity.startActivity(SplashActivity.this);
                } else {
                    EnterCredentialsActivity.startActivity(SplashActivity.this);
                }
            } else if (!sessionManager.isLoggedIn() && sessionManager.wasOnBoardingShown()) {
                WelcomeActivity.startActivity(SplashActivity.this);
            }
            finish();
        }, 500);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return injector;
    }
}
