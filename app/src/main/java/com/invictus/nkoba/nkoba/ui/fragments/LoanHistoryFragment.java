package com.invictus.nkoba.nkoba.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.adapters.LoanHistoryAdapter;
import com.invictus.nkoba.nkoba.models.LoanHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invictus on 1/12/18.
 */

public class LoanHistoryFragment extends Fragment {

    RecyclerView recyclerView;

    LoanHistoryAdapter adapter;
    List<LoanHistoryModel> models = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LoanHistoryAdapter(models);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loan_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);

        setUpRecyclerView();
        fakeData();

    }

    public void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void fakeData() {
        for (int i = 0; i < 15; i++) {
            models.add(new LoanHistoryModel());
        }
        adapter.notifyDataSetChanged();
    }
}
