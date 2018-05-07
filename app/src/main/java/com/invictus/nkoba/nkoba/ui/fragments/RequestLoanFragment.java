package com.invictus.nkoba.nkoba.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.adapters.PaymentSettingAdapter;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;
import com.invictus.nkoba.nkoba.models.Amount;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.models.StateResponse;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by invictus on 1/6/18.
 */

public class RequestLoanFragment extends Fragment {

    @Inject
    KoobaServerApi koobaServerApi;

    @BindView(R.id.request_ripple)
    RippleView requestBtn;

    @BindView(R.id.loan_limit_price)
    TextView loanLimitTextView;

    @BindView(R.id.payments_setting_spinner)
    Spinner paymentSettingSpinner;

    @BindView(R.id.loan_request_amount_seekbar)
    SeekBar loanAmountSeekBar;

    @BindView(R.id.show_intended_loan)
    TextView showIntendeLoanTextView;

    @BindView(R.id.request_btn)
    AppCompatButton requestAppCompatButton;

    PaymentSettingAdapter adapter;
    private StateResponse stateResponse;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        requestBtn.setOnRippleCompleteListener(e -> {
            if (requestAppCompatButton.isEnabled()) {
                requestBtnEvent();
            } else {
                Toast.makeText(getActivity(), "Please give loan amount", Toast.LENGTH_SHORT).show();
            }
        });
        fetchLimit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_loan, container, false);
    }

    @OnClick(R.id.request_btn)
    public void requestBtnEvent() {
        EventBus.getDefault().post(new RequestButtonClicked());
    }


    private void fetchLimit() {
        koobaServerApi.getKoobaState().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        if (response.code() == 200 || response.code() == 201) {
                            String responseJson = new Gson().toJson(response.body());
                            stateResponse = new Gson()
                                    .fromJson(responseJson, StateResponse.class);

                            int cash = stateResponse.getData().getLoanLimit().getAmount().getCents() / 100;

                            loanLimitTextView.setText("KSH " + cash);
                            setSpinnerAdapter(0);
                            setupSeekbar();
                        }
//                        getActivity().finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("TAG", "error occured", t);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void setSpinnerAdapter(int amountCents) {

        List<LoanSetting> filtedTemp = new ArrayList<>();

        for (LoanSetting setting : stateResponse.getData().getLoanSettings()) {
            if (setting.getMinAmount().getCents() <= amountCents) {
                filtedTemp.add(setting);
            }
        }

        adapter = new PaymentSettingAdapter(getActivity(),
                R.layout.payments_setting_spinner_layout, filtedTemp);
        paymentSettingSpinner.setAdapter(adapter);
    }

    int loanMeAmount = 0;

    private void setupSeekbar() {
        loanAmountSeekBar.setMax(100);
        Amount amount = stateResponse.getData().getLoanLimit().getAmount();

        float limit = amount.getCents() / 100;
        float oneTickValue = limit / 100;

        showIntendeLoanTextView
                .setText(amount.getCurrency() + " " + 0);

        loanAmountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                loanMeAmount = Math.round(i * oneTickValue);
                showIntendeLoanTextView
                        .setText(amount.getCurrency() + " " + loanMeAmount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int temp = loanMeAmount * 100;
                setSpinnerAdapter(temp);

                if (temp >= 10000) {
                    requestAppCompatButton.setEnabled(true);
                } else {
                    requestAppCompatButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
