package com.invictus.nkoba.nkoba.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.invictus.nkoba.nkoba.utils.ScreenUtils;

/**
 * Created by invictus on 1/3/18.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make status bar transparent
        ScreenUtils.changeStatusBarColor(this);
        ScreenUtils.makeFullScreen(this);

        new Handler().postDelayed(() ->
                MainActivity.startActivity(this), 500);

    }
}
