package com.invictus.nkoba.nkoba.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.invictus.nkoba.nkoba.R;

/**
 * Created by invictus on 1/5/18.
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }
}
