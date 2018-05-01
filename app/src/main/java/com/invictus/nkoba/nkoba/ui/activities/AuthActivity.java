package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.SessionResponse;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;
import timber.log.Timber;


public class AuthActivity extends DaggerAppCompatActivity {


    public static final String AUTH_EXTRA = "auth_extra";
    private static final String TAG = AuthActivity.class.getSimpleName();

    public static final String AUTH_ERROR = "auth_error";
    public static final String AUTH_SUCCESS = "auth_success";
    public static final String AUTH_RESPONSE = "auth_response";


    @Inject
    KoobaServerApi koobaServerApi;

    /**
     * A translucent activity showing loading status and fetching token
     * Start an activity to enter user details or show details
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (getIntent().getExtras() == null) {
            Toast.makeText(this, "sorry something when wrong", Toast.LENGTH_SHORT).show();
            return;
        }

        String auth = getIntent().getStringExtra(AUTH_EXTRA);
        Timber.e(auth);

        handleCall(auth);
    }

    private void handleCall(String auth) {
        koobaServerApi.authenticate(auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        if (response.code() == 200 || response.code() == 201) {
                            String responseJson = new Gson().toJson(response.body());
                            Intent intent = new Intent();
                            intent.setAction(AUTH_SUCCESS);
                            intent.putExtra(AUTH_RESPONSE, responseJson);

                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }

                        Intent intent = new Intent();
                        intent.setAction(AUTH_ERROR);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                        // add all these to database
                        // user etc
                        // check if user has provided details
                        // set the logged in true and store token in preference


                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "Error occured", t);

                        Intent intent = new Intent();
                        intent.setAction(AUTH_ERROR);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
