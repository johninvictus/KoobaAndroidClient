package com.invictus.nkoba.nkoba.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invictus.nkoba.nkoba.R;

/**
 * Created by invictus on 5/7/18.
 */

public class SearchCustomDialog extends DialogFragment {

    public static SearchCustomDialog getInstance() {
        return new SearchCustomDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_custom_dialog_layout, container, false);
    }

}
