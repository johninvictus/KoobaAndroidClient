package com.invictus.nkoba.nkoba.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by invictus on 1/6/18.
 */

public class RequestLoanFragment extends Fragment {

    @BindView(R.id.request_ripple)
    RippleView requestBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        requestBtn.setOnRippleCompleteListener(e -> sendClickEvent() );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_loan, container, false);
    }

    private void sendClickEvent(){
        EventBus.getDefault().post(new RequestButtonClicked());
    }
}
