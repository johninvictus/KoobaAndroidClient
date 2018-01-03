package com.invictus.nkoba.nkoba.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.onboarding.OnboardingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> OnboardingActivity.startActivity(this), 400);
    }


    public static void startActivity(Activity context) {
        context.startActivity(new Intent(context, MainActivity.class));
        context.finish();
    }
}
