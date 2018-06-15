package com.invictus.nkoba.nkoba.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.LoanPayment;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.models.LoanTaken;
import com.invictus.nkoba.nkoba.models.LoanTakenPaymentResponse;
import com.invictus.nkoba.nkoba.ui.adapters.PaymentHistoryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;

public class LoanDetailActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_principle)
    TextView tv_principle;

    @BindView(R.id.tv_interest)
    TextView tv_interest;

    @BindView(R.id.tv_total_loan)
    TextView tv_total_loan;

    @BindView(R.id.tv_setting_name)
    TextView tv_setting_name;

    @BindView(R.id.loan_payments_recyclerview)
    RecyclerView loan_payments_recyclerview;

    @Inject
    KoobaServerApi koobaServerApi;

    PaymentHistoryAdapter adapter;
    public static final String LOAN_ID = "loan_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail_layout);
        ButterKnife.bind(this);
        adapter = new PaymentHistoryAdapter();
        setupToolbar();
        initRecyclerview();
        int loan_id = getIntent().getIntExtra(LOAN_ID, 0);
        loadFromApi(loan_id);
    }

    private void initRecyclerview() {
        loan_payments_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        loan_payments_recyclerview.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loan Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
    }


    private void loadFromApi(int loan_id) {
        koobaServerApi.getSingleLoan(loan_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        String jsonString = new Gson().toJson(objectResponse.body());
                        LoanTakenPaymentResponse response =
                                new Gson().fromJson(jsonString, LoanTakenPaymentResponse.class);

                        populated(response);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void populated(LoanTakenPaymentResponse paymentResponse) {
        LoanTaken loanTaken = paymentResponse.getData().getLoanTaken();
        String principle = String.valueOf(Math.round(loanTaken.getLoanAmount().getCents() / 100));
        tv_principle.setText(principle);

        //set interest
        String interest = String.valueOf(Math.round(loanTaken.getLoanInterest().getCents() / 100));
        tv_interest.setText(interest);

        //total
        String total = String.valueOf(Math.round(loanTaken.getLoanTotal().getCents() / 100));
        tv_total_loan.setText(total);

        //setting name
        LoanSetting loanSetting = paymentResponse.getData().getLoanSetting();
        String setting_name = loanSetting.getName();

        tv_setting_name.setText(setting_name);

        setupRecyclerview(paymentResponse.getData().getLoanPayments());
    }

    public void setupRecyclerview(List<LoanPayment> loanPayments) {
        adapter.setData(loanPayments);
    }
}
