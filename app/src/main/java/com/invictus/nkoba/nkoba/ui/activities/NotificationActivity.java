package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.adapters.NotificationsAdapter;
import com.invictus.nkoba.nkoba.models.NotificationModel;

import java.util.ArrayList;

/**
 * Created by invictus on 1/9/18.
 */

public class NotificationActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    NotificationsAdapter adapter;
    private ArrayList<NotificationModel> models = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new NotificationsAdapter(this, models);
        setUpToolbar();
        setRecyclerView();
    }


    private void setUpToolbar() {
        toolbar.setTitle("Notifications");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        for(int i = 0; i < 2; i++){
            models.add(new NotificationModel("2017-02-12 08:00",
                    "Courier is out to delivery your order",true));
        }
        for(int i = 0; i < 10; i++){
            models.add(new NotificationModel("2017-02-12 08:00",
                    "Courier is out to delivery your order",false));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, NotificationActivity.class));
    }
}
