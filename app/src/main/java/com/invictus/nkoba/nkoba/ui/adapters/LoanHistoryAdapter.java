package com.invictus.nkoba.nkoba.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invictus on 1/12/18.
 */

public class LoanHistoryAdapter extends RecyclerView.Adapter<LoanHistoryAdapter.ViewHolder> {

    private List<LoanHistoryModel> models = new ArrayList<>();


    public LoanHistoryAdapter(List<LoanHistoryModel> models) {
        this.models = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_loan_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
