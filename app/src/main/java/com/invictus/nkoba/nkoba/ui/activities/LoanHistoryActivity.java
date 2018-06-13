package com.invictus.nkoba.nkoba.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.LoanHistoryResponse;
import com.invictus.nkoba.nkoba.models.LoansTaken;
import com.invictus.nkoba.nkoba.ui.adapters.LoanHistoryAdapter;
import com.vlonjatg.progressactivity.ProgressFrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

/**
 * Created by invictus on 6/8/18.
 */

public class LoanHistoryActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressActivity)
    ProgressFrameLayout progressFrameLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.loan_taken_header)
    LinearLayout loan_taken_header;

    LoanHistoryAdapter adapter;

    @Inject
    KoobaServerApi koobaServerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_history);
        ButterKnife.bind(this);

        setupToolbar();

        adapter = new LoanHistoryAdapter();
        setUpRecyclerView();
        loanFromApi(1, false);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Loan History");
    }

    public void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }


    public void loanFromApi(int page, boolean loadMore) {
        setMessageState("LOADING");
        koobaServerApi.getUserLoanHistory(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        if (response.code() == 200 || response.code() == 201) {
                            String responseJson = new Gson().toJson(response.body());
                            LoanHistoryResponse historyResponse = new Gson().fromJson(responseJson, LoanHistoryResponse.class);
                            if (historyResponse.getLoansTaken().size() == 0) {
                                setMessageState("EMPTY");
                            } else {
                                // do something
                                List<LoansTaken> loansTakens = historyResponse.getLoansTaken();
                                if (loadMore) {
                                    adapter.addItems(loansTakens);
                                } else {
                                    adapter.provideNewData(loansTakens);
                                }
                                setMessageState("CONTENT");
                            }
                        } else {
                            setMessageState("SERVER_ERROR");
                        }
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
        Intent intent = new Intent(context, LoanHistoryActivity.class);
        context.startActivity(intent);
    }

    private void setMessageState(String state) {
        switch (state) {
            case "LOADING":
                loan_taken_header.setVisibility(View.GONE);
                progressFrameLayout.showLoading();
                break;

            case "EMPTY":
                loan_taken_header.setVisibility(View.GONE);
                progressFrameLayout.showEmpty(R.drawable.empty_box,
                        "No notifications",
                        "No notification has been sent by kooba yet");
                break;

            case "NETWORK_ERROR":
                loan_taken_header.setVisibility(View.GONE);
                progressFrameLayout.showError(R.drawable.ic_wifi_off,
                        "No Connection",
                        "We could not establish a connection with our servers. Please try again when you are connected to the internet.",
                        "Try Again", retryInternetConnection);
                break;

            case "SERVER_ERROR":
                loan_taken_header.setVisibility(View.GONE);
                progressFrameLayout.showError(R.drawable.ic_server_connection_off,
                        "Server Error",
                        "We could not establish a connection with our servers. Please try again after some time",
                        "Try Again", retryInternetConnection);
                break;
            case "CONTENT":
                loan_taken_header.setVisibility(View.VISIBLE);
                progressFrameLayout.showContent();
                break;
        }
    }

    private View.OnClickListener retryInternetConnection = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loanFromApi(1, false);
        }
    };


}
