package com.invictus.nkoba.nkoba.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invictus.nkoba.nkoba.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invictus on 5/21/18.
 */

public class LoanScheduesAdapter extends RecyclerView.Adapter<LoanScheduesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> loanSchedues = new ArrayList<>();


    public LoanScheduesAdapter(Context context, ArrayList<String> loanSchedues) {
        this.context = context;
        this.loanSchedues = loanSchedues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_loan_schedue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return loanSchedues.size();
        return 4;
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

        public void populate(String cashString, String dateString) {
            cashTextview.setText(cashString);
            dateSchedueTextview.setText(dateString);
        }
    }
}
