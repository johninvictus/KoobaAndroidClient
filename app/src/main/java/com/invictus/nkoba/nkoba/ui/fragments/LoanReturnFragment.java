package com.invictus.nkoba.nkoba.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.LoanPayment;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.models.LoanTaken;
import com.invictus.nkoba.nkoba.models.LoanTakenPaymentResponse;
import com.invictus.nkoba.nkoba.utils.DateTimeUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by invictus on 1/6/18.
 */

public class LoanReturnFragment extends Fragment {

    @BindView(R.id.principal_loan_tv)
    TextView principalLoanTv;

    @BindView(R.id.loan_interest_tv)
    TextView loanInterestTv;

    @BindView(R.id.next_loan_payment_date_tv)
    TextView nextLoanPaymentDateTv;

    @BindView(R.id.loan_remaining_tv)
    TextView loanRemainingTv;

    @BindView(R.id.pending_status_layout)
    LinearLayout pendingStatusLayout;

    /*
    * views for payment schedule card
    * **/
    @BindView(R.id.currency_tv)
    TextView currencyTv;

    @BindView(R.id.tab_loan_tv)
    TextView tabLoanTv;

    @BindView(R.id.pay_loan_rv)
    RippleView payLoanRv;

    @BindView(R.id.pay_loan_btn)
    AppCompatButton payLoanBtn;


    @Inject
    KoobaServerApi koobaServerApi;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_loan, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private PieChart pieChart;
    private TabLayout loanSchedue;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChart = view.findViewById(R.id.loan_break_down);
        loanSchedue = view.findViewById(R.id.tabs);

        setUpPieChart();
//        setUpTabs();
        fetchLoanPayments();
    }

    private void setUpPieChart() {
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        //pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);

        pieChart.setHighlightPerTapEnabled(true);


        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        // pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(10f);

        pieChart.setTouchEnabled(false);
    }


    private void setUpTabs() {
        loanSchedue.addTab(loanSchedue.newTab().setText("1 May"));
        loanSchedue.addTab(loanSchedue.newTab().setText("8 May"));
        loanSchedue.addTab(loanSchedue.newTab().setText("15 May"));
        loanSchedue.addTab(loanSchedue.newTab().setText("25 May"));


        //disable a location.
        LinearLayout tabStrip = (LinearLayout) loanSchedue.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tabStrip.getChildAt(0).setBackgroundColor(Color.CYAN);
                    return true;
                }
            });
        }

    }


    private void fetchLoanPayments() {
        koobaServerApi.getLoanPayment()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {
                    @Override
                    public void onNext(Response<Object> response) {
                        if (response.code() == 200) {
                            decodeResponse(response);
                        } else {
                            message("An error occurred");
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void decodeResponse(Response<Object> response) {
        String jsonString = new Gson().toJson(response.body());
        LoanTakenPaymentResponse loanTakenPaymentResponse =
                new Gson().fromJson(jsonString, LoanTakenPaymentResponse.class);

        addDataToLoanSummaryCard(loanTakenPaymentResponse);

    }


    private void addDataToLoanSummaryCard(LoanTakenPaymentResponse response) {
        LoanTaken loanTaken = response.getData().getLoanTaken();


        String currency = loanTaken.getLoanAmount().getCurrency();

        String loanPrinciple = currency + " " + (loanTaken.getLoanAmount().getCents() / 100);
        principalLoanTv.setText(loanPrinciple);

        String loanInterest = currency + " " + (loanTaken.getLoanInterest().getCents() / 100);
        loanInterestTv.setText(loanInterest);

        int getNextPaymentId = loanTaken.getNextPaymentId();
        LoanPayment currentPayment = null;
        int totalLoanRemaining = 0;
        for (LoanPayment payment : response.getData().getLoanPayments()) {
            if (payment.getId() == getNextPaymentId) {
                currentPayment = payment;
            }
            totalLoanRemaining = totalLoanRemaining + payment.getAmount().getCents();
        }

        nextLoanPaymentDateTv.setText(currentPayment.getPaymentSchedue());

        String loanRemaining = currency + " " + (totalLoanRemaining / 100);
        loanRemainingTv.setText(loanRemaining);

        // TODO: write logic for all status
        if (loanTaken.getStatus().equals("pending")) {
            pendingStatusLayout.setVisibility(View.VISIBLE);
        } else {
            pendingStatusLayout.setVisibility(View.GONE);
        }

        // setup pie chart
        setupLoanPieData(response);
    }

    /**
     * resources
     * https://stackoverflow.com/a/27638188/4034026
     **/
    private void setupLoanPieData(LoanTakenPaymentResponse response) {
        LoanTaken loanTaken = response.getData().getLoanTaken();
        LoanSetting loanSetting = response.getData().getLoanSetting();

        String totalLoanString = loanTaken.getLoanTotal().getCurrency()
                + " " +
                (loanTaken.getLoanTotal().getCents() / 100)
                + " \nTotal loan";

        float mult = 100;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(loanSetting.getInterest(), "Interest", getResources().getDrawable(R.drawable.star)));
        entries.add(new PieEntry((100 - loanSetting.getInterest()), "Loan", getResources().getDrawable(R.drawable.star)));

        // pieChart.setCenterTextTypeface(mTfLight);
        pieChart.setCenterText(new SpannableString(totalLoanString));

        PieDataSet dataSet = new PieDataSet(entries, null);

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setDrawValues(false);

        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();

        /*
        * setup tabs
        * **/
        setupLoanPaymentTabs(response);
    }


    /**
     * setup payment strip with data from the server
     **/
    private void setupLoanPaymentTabs(LoanTakenPaymentResponse response) {
        /*
        * Adding tabs
        * **/
        List<LoanPayment> loanPayments = response.getData().getLoanPayments();
        for (LoanPayment payment : loanPayments) {


            if (loanPayments.size() == 1) {
                String tabDate = DateTimeUtils.parseDateTime(payment.getPaymentSchedue(), "dd-MM-yyyy", "dd MMM yyyy");
                loanSchedue.addTab(loanSchedue.newTab().setText(tabDate));
            } else {
                String tabDate = DateTimeUtils.parseDateTime(payment.getPaymentSchedue(), "dd-MM-yyyy", "dd MMM");
                loanSchedue.addTab(loanSchedue.newTab().setText(tabDate));
            }
        }

        /*
        * Write logic to disable button and change text for prices
        * **/



        /*
        * Set initial selected tab
        * **/
        int currentID = response.getData().getLoanTaken().getNextPaymentId();
        int currentPosition = getCurrentPayablePayment(currentID, response.getData()
                .getLoanPayments());


        setDataOnPayLoanCard(loanPayments, currentPosition);

        loanSchedue.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setDataOnPayLoanCard(loanPayments, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setDataOnPayLoanCard(List<LoanPayment> loanPayments, int position) {
        LoanPayment payment = loanPayments.get(position);
        loanSchedue.getTabAt(position).select();
        currencyTv.setText(payment.getAmount().getCurrency());
        int showPayment = getPaymentToShow(loanPayments, position);

        tabLoanTv.setText(String.valueOf(showPayment));

                /*
                * Disable, enable, change text on button
                * **/
        if (showPayment > 0) {
            payLoanBtn.setEnabled(true);
            payLoanBtn.setText("Pay Loan");
        } else {
            payLoanBtn.setEnabled(false);
            payLoanBtn.setText("Loan Payed");
        }

        payLoanRv.setOnRippleCompleteListener(l -> {
            if (payLoanBtn.isEnabled()) {
                message("pay this amount " + showPayment);
            } else {
                message("You already paid this amount");
            }
        });

    }

    private int getPaymentToShow(List<LoanPayment> payments, int position) {
        int temp = 0;
        for (int i = 0; i < payments.size(); i++) {
            if (i <= position) {
                LoanPayment payment = payments.get(i);
                temp = temp + (payment.getPaymentRemaining().getCents() / 100);
            }
        }

        return temp;
    }

    private int getCurrentPayablePayment(int id, List<LoanPayment> payments) {
        int position = 0;

        for (int i = 0; i < payments.size(); i++) {
            LoanPayment x = payments.get(i);
            if (x.getId().equals(id)) {
                position = i;
            }
        }

        return position;
    }

    private void message(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
