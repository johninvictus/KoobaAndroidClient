package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;
import com.invictus.nkoba.nkoba.ui.fragments.LoanReturnFragment;
import com.invictus.nkoba.nkoba.ui.fragments.RequestLoanFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
                    .add(R.id.fragment_container, new RequestLoanFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                ProfileActivity.startActivity(this);
                return true;

            case R.id.action_notification:
                NotificationActivity.startActivity(this);
                return true;

            case R.id.action_about:
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static void startActivity(Activity context) {
        context.startActivity(new Intent(context, MainActivity.class));
        context.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RequestButtonClicked event) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoanReturnFragment())
                .commit();
    }
}
