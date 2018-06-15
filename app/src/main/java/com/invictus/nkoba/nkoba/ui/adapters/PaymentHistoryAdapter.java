package com.invictus.nkoba.nkoba.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanPayment;
import com.invictus.nkoba.nkoba.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private ArrayList<LoanPayment> models = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_payment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populate(models.get(position));
    }

    public void setData(List<LoanPayment> data) {
        models.clear();
        models.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_payment_date)
        TextView tv_payment_date;

        @BindView(R.id.tv_payment_amount)
        TextView tv_payment_amount;

        @BindView(R.id.tv_payment_status)
        TextView tv_payment_status;

        @BindView(R.id.ln_payment_history)
        LinearLayout ln_payment_history;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void populate(LoanPayment payment) {
            if(getAdapterPosition()%2 == 0){
                //dark
                ln_payment_history.setBackgroundColor(Color.parseColor("#d3d4d7"));
            }else{
                // light
                ln_payment_history.setBackgroundColor(Color.parseColor("#fdfeff"));
            }

            String dateString = DateTimeUtils.parseDateTime(
                    payment.getPaymentSchedue(),
                    "dd-MM-yyyy",
                    "dd/MM/yyyy");

            tv_payment_date.setText(dateString);

            String currency = payment.getAmount().getCurrency();

            String amountString = Math.round(payment.getAmount().getCents() / 100) + " " + currency;
            tv_payment_amount.setText(amountString);

            Context context = tv_payment_status.getContext();

            if (payment.getStatus() == "paid") {
                tv_payment_status.setTextColor(context.getResources().getColor(R.color.colorAccent));
            } else {
                tv_payment_status.setTextColor(Color.parseColor("#808080"));
            }

            tv_payment_status.setText(payment.getStatus());
        }
    }
}
