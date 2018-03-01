package com.invictus.nkoba.nkoba.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.andexert.library.RippleView;
import com.invictus.nkoba.nkoba.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by invictus on 2/28/18.
 */

public class EnterCredentialsActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, HasActivityInjector {

    private Toolbar toolbar;
    private EditText legalName;
    private EditText IDNumber;
    private EditText birthDate;
    private TextInputLayout birthDateLayout;

    private RippleView saveBtn;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_credentials);

        toolbar = findViewById(R.id.toolbar);
        legalName = findViewById(R.id.input_name);
        IDNumber = findViewById(R.id.input_number);
        birthDate = findViewById(R.id.input_date);
        saveBtn = findViewById(R.id.save_btn);
        birthDateLayout = findViewById(R.id.date_textinput_layout);

        setupToolbar();

        saveBtn.setOnRippleCompleteListener(rippleView -> saveUserDetails());
        birthDate.setOnClickListener(e -> startDatePicker());
    }

    private void setupToolbar() {
        toolbar.setTitle("Enter your credentials");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
    }

    private void startDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        dialog.setTitle("Enter date of birth");
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("dd MM yyy");
        birthDate.setText(format.format(calendar.getTime()));
    }


    private void saveUserDetails() {
        String id_number_string = IDNumber.getText().toString();

        String name = legalName.getText().toString();
        String birth = birthDate.getText().toString();

        if (name.isEmpty() && birth.isEmpty() && id_number_string.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int id_number = Integer.valueOf(id_number_string);

        //send to the Api

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;

    }
}
