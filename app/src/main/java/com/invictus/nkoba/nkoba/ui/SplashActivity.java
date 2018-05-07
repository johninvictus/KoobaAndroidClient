package com.invictus.nkoba.nkoba.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.ui.activities.EnterCredentialsActivity;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;
import com.invictus.nkoba.nkoba.ui.activities.OnboardingActivity;
import com.invictus.nkoba.nkoba.ui.activities.WelcomeActivity;
import com.invictus.nkoba.nkoba.utils.ScreenUtils;
import com.invictus.nkoba.nkoba.utils.SessionManager;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by invictus on 1/3/18.
 */

public class SplashActivity extends DaggerAppCompatActivity {
    private final int POST_DELAY = 500;

    public static final String STATE_INFO = "state_info";
    public static final String Loan_INFO = "state_info";
    public static final String LOAN_STATE_DATA = "loan_state_data";

    @Inject
    SessionManager sessionManager;

    @Inject
    KoobaServerApi koobaServerApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        //make status bar transparent
        ScreenUtils.changeStatusBarColor(this);
        ScreenUtils.makeFullScreen(this);

        if (!sessionManager.wasOnBoardingShown()) {
            startOnboardingActivity();
        } else if (sessionManager.isLoggedIn() && sessionManager.wasOnBoardingShown()) {
            Timber.e(sessionManager.getAuthToken());
            if (sessionManager.getIsDetailsProvided()) {
                MainActivity.startActivity(SplashActivity.this);

            } else {
                startEnterCredentialsActivity();
            }
        } else if (!sessionManager.isLoggedIn() && sessionManager.wasOnBoardingShown()) {
            startWelcomeActivity();
        }
    }

    private void startOnboardingActivity() {
        new Handler().postDelayed(() -> {
            sessionManager.setOnBoardingShown(true);
            OnboardingActivity.startActivity(this);
        }, POST_DELAY);
    }

    private void startEnterCredentialsActivity() {
        new Handler().postDelayed(() ->
                        EnterCredentialsActivity.startActivity(SplashActivity.this),
                POST_DELAY);
    }

    private void startWelcomeActivity() {
        new Handler().postDelayed(() ->
                        WelcomeActivity.startActivity(SplashActivity.this),
                POST_DELAY);
    }

}
