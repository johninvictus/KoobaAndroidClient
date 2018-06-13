package com.invictus.nkoba.nkoba.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.Credentials;
import com.invictus.nkoba.nkoba.models.LoansTaken;
import com.invictus.nkoba.nkoba.models.User;
import com.invictus.nkoba.nkoba.models.UserProfileResponse;
import com.invictus.nkoba.nkoba.ui.adapters.LoanHistoryAdapter;
import com.vlonjatg.progressactivity.ProgressFrameLayout;

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
 * Created by invictus on 6/3/18.
 */

public class UserProfileActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressActivity)
    ProgressFrameLayout progressFrameLayout;

    @BindView(R.id.full_name)
    TextView full_name;

    @BindView(R.id.mobile_number)
    TextView mobile_number;

    @BindView(R.id.input_legal_name)
    EditText input_legal_name;

    @BindView(R.id.input_mobile_number)
    EditText input_mobile_number;

    @BindView(R.id.input_legal_number)
    EditText input_legal_number;

    @BindView(R.id.input_birth_date)
    EditText input_birth_date;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    KoobaServerApi koobaServerApi;

    LoanHistoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        adapter = new LoanHistoryAdapter();
        setupToolbar();
        fetchApi();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    public void fetchApi() {
        setMessageState("LOADING");
        koobaServerApi.getUserProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        String responseString = new Gson().toJson(objectResponse.body());
                        UserProfileResponse profileResponse =
                                new Gson().fromJson(responseString, UserProfileResponse.class);

                        populateViews(profileResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        setMessageState("SERVER_ERROR");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setMessageState(String state) {
        switch (state) {
            case "LOADING":
                progressFrameLayout.showLoading();
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
            fetchApi();
        }
    };


    public void populateViews(UserProfileResponse userProfileResponse) {
        Credentials credentials = userProfileResponse.getCredentials();
        User user = userProfileResponse.getUser();

        full_name.setText(credentials.getFullName());
        String mobileString = "+" + user.getCountryPrefix() + " " + user.getNationalNumber();
        mobile_number.setText(mobileString);

        //inputs
        input_legal_name.setText(credentials.getFullName());
        input_mobile_number.setText(mobileString);
        input_legal_number.setText(String.valueOf(credentials.getNationalCard()));
        input_birth_date.setText(credentials.getBirthDate());

        popupateRecyclerview(userProfileResponse.getLoansTaken());

        //show content
        setMessageState("CONTENT");
    }

    private void popupateRecyclerview(List<LoansTaken> loansTaken) {
        if(loansTaken.size() == 0){
            // empty message

        }else {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            adapter.provideNewData(loansTaken);
        }
    }
}
