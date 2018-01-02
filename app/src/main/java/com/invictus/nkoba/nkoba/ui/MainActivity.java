package com.invictus.nkoba.nkoba.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(()-> OnboardingActivity.startActivity(this), 400);
    }
}
