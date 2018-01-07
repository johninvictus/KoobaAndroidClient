package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.fragments.LoanReturnFragment;
import com.invictus.nkoba.nkoba.ui.fragments.RequestLoanFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Handler handler = new Handler();
        //handler.postDelayed(() -> OnboardingActivity.startActivity(this), 400);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new LoanReturnFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public static void startActivity(Activity context) {
        context.startActivity(new Intent(context, MainActivity.class));
        context.finish();
    }
}
