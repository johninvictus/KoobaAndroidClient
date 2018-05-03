package com.invictus.nkoba.nkoba.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.events.RequestButtonClicked;
import com.invictus.nkoba.nkoba.models.StateResponse;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;

import org.greenrobot.eventbus.EventBus;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        requestBtn.setOnRippleCompleteListener(e -> sendClickEvent());
        fetchLimit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_loan, container, false);
    }

    private void sendClickEvent() {
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
                            StateResponse stateResponse = new Gson()
                                    .fromJson(responseJson, StateResponse.class);

                            Timber.e(responseJson);

                            int cash = stateResponse.getData().getLoanLimit().getAmount().getCents() / 100;

                            loanLimitTextView.setText("KSH " + cash);
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

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }
}
