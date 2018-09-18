package com.invictus.nkoba.nkoba.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;
import com.invictus.nkoba.nkoba.models.Amount;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.models.StateResponse;
import com.invictus.nkoba.nkoba.ui.adapters.PaymentSettingAdapter;
import com.invictus.nkoba.nkoba.ui.dialog.LoanDetailBottomSheetFragment;
import com.invictus.nkoba.nkoba.ui.dialog.SearchCustomDialog;
import com.invictus.nkoba.nkoba.utils.ScreenUtils;

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

    @BindView(R.id.share_layout)
    CardView share_layout;


    DialogFragment dialogFragment;

    PaymentSettingAdapter adapter;
    private StateResponse stateResponse;
    private LoanSetting loanSetting = null;
    int loanMeAmount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        dialogFragment = SearchCustomDialog.getInstance();
        dialogFragment.setCancelable(false);

        requestBtn.setOnRippleCompleteListener(e -> {
            if (requestAppCompatButton.isEnabled()) {
                requestBtnEvent();
            } else {
                Toast.makeText(getActivity(), "Please give loan amount", Toast.LENGTH_SHORT).show();
            }
        });


//        // hide the share card incase it is not visible on small screens
//        if (!ScreenUtils.isViewVisible(share_layout, getActivity())) {
//            share_layout.setVisibility(View.GONE);
//        } else {
//            share_layout.setVisibility(View.VISIBLE);
//        }


        fetchLimit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_loan, container, false);
    }

    public void requestBtnEvent() {
        requestLoan(loanSetting.getId(), loanMeAmount);
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

        paymentSettingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loanSetting = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loanSetting = adapter.getItem(0);
            }
        });
    }


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

    private void requestLoan(int loanSettingId, int amount) {
        String amoutString = amount + ".00";
        dialogFragment.show(getFragmentManager(), "fragment");

        koobaServerApi.requestLoan(loanSettingId, amoutString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        String responseJson = new Gson().toJson(response.body());
                        Timber.e(responseJson);
                        int code = response.code();
                        if (code == 200) {
                            startLoanHistoryFragment();
                        } else if (code == 422) {
                            message("You might have another pending loan or you have not provided all fields");

                        } else if (code == 500) {
                            message("An error occured internally, please try again later");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        dialogFragment.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialogFragment.dismiss();
                        Timber.e("On complete ");
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    private void startLoanHistoryFragment() {
        EventBus.getDefault().post(new RequestButtonClicked());
    }

    private void message(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.open_loan_detail_btn)
    public void openLoanDetail() {

        LoanDetailBottomSheetFragment sheetFragment =
                LoanDetailBottomSheetFragment.getInstance(loanMeAmount, loanSetting);

        sheetFragment.show(getActivity().getSupportFragmentManager(), sheetFragment.getTag());
    }

    @OnClick(R.id.share_layout)
    public void shareLayout() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "hey kids check out this cool link\n" +
                "https://example.org/cool-link");

        startActivity(Intent.createChooser(shareIntent, "Share with"));
    }


}


