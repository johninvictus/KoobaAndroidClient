package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.Notification;
import com.invictus.nkoba.nkoba.models.NotificationModel;
import com.invictus.nkoba.nkoba.models.NotificationsResponse;
import com.invictus.nkoba.nkoba.ui.adapters.NotificationsAdapter;
import com.vlonjatg.progressactivity.ProgressFrameLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Created by invictus on 1/9/18.
 */

public class NotificationActivity extends DaggerAppCompatActivity {

    NotificationsAdapter adapter;
    private ArrayList<NotificationModel> models = new ArrayList<>();

    @Inject
    KoobaServerApi koobaServerApi;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.progressActivity)
    ProgressFrameLayout progressFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        refreshLayout.setOnRefreshListener(onRefreshListener);

        setUpToolbar();
        setRecyclerView();
        initLayoutData();
    }

    private void initLayoutData() {
        loadData(1, true);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadData(1, true);
        }
    };


    private void setUpToolbar() {
        toolbar.setTitle("Notifications");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setRecyclerView() {
        adapter = new NotificationsAdapter(this, models);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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


    private void loadData(int page, boolean refreshing) {
        refreshLayout.setRefreshing(true);
        koobaServerApi.getUserNotification(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        if (response.code() == 200 || response.code() == 201) {
                            String responseJson = new Gson().toJson(response.body());
                            NotificationsResponse notificationsResponse =
                                    new Gson().fromJson(responseJson, NotificationsResponse.class);
                            populateAdapter(notificationsResponse.getNotifications(), refreshing);
                        } else {
                            setMessageState("SERVER_ERROR");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        refreshLayout.setRefreshing(false);
                        setMessageState("SERVER_ERROR");
                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void populateAdapter(List<Notification> notifications, boolean refreshing) {

        if(notifications.size() == 0){
            setMessageState("EMPTY");
            return;
        }

        List<NotificationModel> temp = new ArrayList<>();
        for (Notification notification : notifications) {
            temp.add(new NotificationModel(notification.getDate(),
                    notification.getMessage(), false));
        }
        if (refreshing) {
            adapter.setRefreshData(temp);
        } else {
            adapter.setLoadingMoreData(temp);
        }

        setMessageState("CONTENT");
    }

    private void setMessageState(String state){
        switch (state){
            case "EMPTY":
                progressFrameLayout.showEmpty(R.drawable.empty_box,
                        "No notifications",
                        "No notification has been sent by kooba yet");
                break;

            case "NETWORK_ERROR":
                progressFrameLayout.showError(R.drawable.ic_wifi_off,
                        "No Connection",
                        "We could not establish a connection with our servers. Please try again when you are connected to the internet.",
                        "Try Again", retryInternetConnection);
                break;

            case "SERVER_ERROR":
                progressFrameLayout.showError(R.drawable.ic_server_connection_off,
                        "Server Error",
                        "We could not establish a connection with our servers. Please try again after some time",
                        "Try Again", retryInternetConnection);
                break;
            case "CONTENT":
                progressFrameLayout.showContent();
                break;
        }
    }

    private View.OnClickListener retryInternetConnection = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loadData(1, true);
        }
    };
}
