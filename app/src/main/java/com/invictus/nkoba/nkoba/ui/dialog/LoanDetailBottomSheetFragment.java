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

import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanSetting;
import com.invictus.nkoba.nkoba.ui.adapters.LoanScheduesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by invictus on 5/20/18.
 */

public class LoanDetailBottomSheetFragment extends BottomSheetDialogFragment {


    @BindView(R.id.loan_schedue_recyclerview)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private LoanScheduesAdapter scheduesAdapter;

    private final String AMOUNT_KEY = "LOAN_AMOUNT";
    private final String LOAN_SETTING_KEY = "LOAN_SETTING";

    public LoanDetailBottomSheetFragment() {
        //required empty constructor
    }

    public LoanDetailBottomSheetFragment getInstance(int amount, LoanSetting setting) {
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
        scheduesAdapter = new LoanScheduesAdapter(getActivity(), new ArrayList<String>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(scheduesAdapter);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
