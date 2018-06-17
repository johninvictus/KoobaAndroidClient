package com.invictus.nkoba.nkoba.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanPaymentPredictModel;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.ui.adapters.LoanScheduesAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by invictus on 5/20/18.
 */

public class LoanDetailBottomSheetFragment extends BottomSheetDialogFragment {


    @BindView(R.id.loan_schedue_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.tv_setting_name)
    TextView tv_setting_name;

    @BindView(R.id.tv_interest)
    TextView tv_interest;

    @BindView(R.id.tv_total_loan)
    TextView tv_total_loan;

    @BindView(R.id.tv_principle)
    TextView tv_principle;

    private Unbinder unbinder;
    private LoanScheduesAdapter scheduesAdapter;

    private final static String AMOUNT_KEY = "LOAN_AMOUNT";
    private final static String LOAN_SETTING_KEY = "LOAN_SETTING";

    public LoanDetailBottomSheetFragment() {
        //required empty constructor
    }

    public static LoanDetailBottomSheetFragment getInstance(int amount, LoanSetting setting) {
        LoanDetailBottomSheetFragment fragment = new LoanDetailBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AMOUNT_KEY, amount);
        String objectString = new Gson().toJson(setting);
        bundle.putString(LOAN_SETTING_KEY, objectString);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_detail_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        setupUI();

        //make sure everything is visible
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = (FrameLayout)
                        dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setPeekHeight(0);
            }
        });
    }

    private void setupUI() {

        LoanSetting setting = new Gson().fromJson(getArguments().getString(LOAN_SETTING_KEY), LoanSetting.class);
        scheduesAdapter = new LoanScheduesAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(scheduesAdapter);

        int amount = getArguments().getInt(AMOUNT_KEY);
        LoanSetting loanSetting = new Gson()
                .fromJson(getArguments().getString(LOAN_SETTING_KEY), LoanSetting.class);
        populateUI(amount, loanSetting);
    }

    private void populateUI(int amount, LoanSetting loanSetting) {
        // round to remove float
        int interest = Math.round((loanSetting.getInterest() * amount) / 100);
        int total_loan = interest + amount;
        String currency = loanSetting.getMinAmount().getCurrency();

        tv_principle.setText(String.valueOf(amount));
        tv_setting_name.setText(loanSetting.getName());

        int length = 0;
        if (loanSetting.getTermMeasure().equals("monthly")) {
            length = 28;
        } else if (loanSetting.getTermMeasure().equals("weekly")) {
            length = 7;
        }

        //algorithm to calculate schedules
        int factor = Math.round(loanSetting.getTerm() / (length * loanSetting.getFrequency()));

        //offset days
        int off_set = Math.round(loanSetting.getTerm() / factor);

        // TODO:
        int equal_payments = Math.round((total_loan / factor) + (factor / off_set));


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        // generate list
        String currentDate = df.format(c);
        ArrayList<LoanPaymentPredictModel> tempList = new ArrayList<>();


        for (int i = 0; i < factor; i++) {
            LoanPaymentPredictModel model = new LoanPaymentPredictModel();
            model.setAmount(equal_payments + " " + currency);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cd = Calendar.getInstance();

            try {
                cd.setTime(sdf.parse(currentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            cd.add(Calendar.DATE, off_set);  // number of days to add
            currentDate = sdf.format(cd.getTime());  // dt is now the new date

            model.setSchedueDate(currentDate);
            tempList.add(model);
        }

        scheduesAdapter.addData(tempList);

        int changedLoanTotal = equal_payments * factor;
        int changedInterest = changedLoanTotal - amount;

        tv_interest.setText(String.valueOf(changedInterest));
        tv_total_loan.setText(String.valueOf(changedLoanTotal));
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
