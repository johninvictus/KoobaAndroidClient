package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;
import com.invictus.nkoba.nkoba.ui.common.MainNavigationController;
import com.invictus.nkoba.nkoba.ui.fragments.LoanReturnFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private Toolbar toolbar;

    @Inject
    MainNavigationController navigationController;

    @Inject
    KoobaServerApi koobaServerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationController.navigateToRequestFragment();
//        fetchLimit();
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
                // ProfileActivity.startActivity(this);
                UserProfileActivity.startActivity(this);
                return true;

            case R.id.action_notification:
                NotificationActivity.startActivity(this);
                return true;

            case R.id.action_loan_history:
                LoanHistoryActivity.startActivity(this);
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
