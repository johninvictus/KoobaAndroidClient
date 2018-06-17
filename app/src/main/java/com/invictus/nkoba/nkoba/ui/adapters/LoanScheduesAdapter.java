package com.invictus.nkoba.nkoba.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanPaymentPredictModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invictus on 5/21/18.
 */

public class LoanScheduesAdapter extends RecyclerView.Adapter<LoanScheduesAdapter.ViewHolder> {

    private ArrayList<LoanPaymentPredictModel> loanSchedues = new ArrayList<>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_loan_schedue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(loanSchedues.get(position));
    }

    @Override
    public int getItemCount() {
        return loanSchedues.size();
    }

    public void addData(ArrayList<LoanPaymentPredictModel> data) {
        loanSchedues.clear();
        loanSchedues.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cash_tv)
        TextView cashTextview;

        @BindView(R.id.date_schedue_tv)
        TextView dateSchedueTextview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void populate(LoanPaymentPredictModel predictModel) {
            cashTextview.setText(predictModel.getAmount());
            dateSchedueTextview.setText(predictModel.getSchedueDate());
        }
    }
}
