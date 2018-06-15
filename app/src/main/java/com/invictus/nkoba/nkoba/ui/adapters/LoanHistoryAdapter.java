package com.invictus.nkoba.nkoba.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoansTaken;
import com.invictus.nkoba.nkoba.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by invictus on 1/12/18.
 */

public class LoanHistoryAdapter extends RecyclerView.Adapter<LoanHistoryAdapter.ViewHolder> {

    private List<LoansTaken> models = new ArrayList<>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_loan_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(models.get(position));
    }

    public void addItems(List<LoansTaken> loansTakens) {
        models.addAll(loansTakens);
        this.notifyDataSetChanged();
    }

    public void provideNewData(List<LoansTaken> loansTakens) {
        models.clear();
        models.addAll(loansTakens);
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.tv_amount)
        TextView tv_amount;

        @BindView(R.id.tv_status)
        TextView tv_status;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void populate(LoansTaken response) {
            int loanAmount = Math.round(response.getLoanAmount().getCents() / 100);
            tv_amount.setText(loanAmount + " " + response.getLoanAmount().getCurrency());

            switch (response.getStatus()) {
                case "pending":
                    // TODO: change colors
            }

            tv_status.setText(response.getStatus());

            // TODO: change date format to have x/xx/xxxx
            String formattedDate = DateTimeUtils.parseDateTime(response.getLoanTakenDate(), "dd-MM-yyyy", "dd/MM/yyyy");
            tv_date.setText(formattedDate);
        }
    }


    public LoansTaken getItem(int position){
        return models.get(position);
    }
}
