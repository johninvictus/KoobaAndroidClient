package com.invictus.nkoba.nkoba.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.models.LoanSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invictus on 5/4/18.
 */

public class PaymentSettingAdapter extends ArrayAdapter<LoanSetting> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<LoanSetting> items;
    private final int mResource;

    public PaymentSettingAdapter(@NonNull Context context, int resource, @NonNull List<LoanSetting> objects) {
        super(context, resource, 0, objects);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);
        TextView payment_setting = view.findViewById(R.id.payment_setting_text);

        LoanSetting setting = items.get(position);
        String setting_string = setting.getName();
        payment_setting.setText(setting_string);
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public LoanSetting getItem(int position) {
        return items.get(position);
    }
}
