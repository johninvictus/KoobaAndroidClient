package com.invictus.nkoba.nkoba.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.andexert.library.RippleView;
import com.google.gson.Gson;
import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.api.KoobaServerApi;
import com.invictus.nkoba.nkoba.models.Credentials;
import com.invictus.nkoba.nkoba.models.CredentialsResponse;
import com.invictus.nkoba.nkoba.utils.SessionManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by invictus on 2/28/18.
 */

public class EnterCredentialsActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, HasActivityInjector, Validator.ValidationListener {

    private Toolbar toolbar;

    @NotEmpty
    private EditText legalName;

    @NotEmpty
    @Length(min = 5, max = 9, trim = true)
    private EditText IDNumber;

    @NotEmpty
    private EditText birthDate;

    private TextInputLayout birthDateLayout;
    private TextInputLayout legalNameInputLayout;
    private TextInputLayout IDNumberInputLayout;

    private ProgressBar progressBar;

    @Inject
    KoobaServerApi koobaServerApi;

    @Inject
    SessionManager sessionManager;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    private Validator validator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_credentials);

        toolbar = findViewById(R.id.toolbar);
        legalName = findViewById(R.id.input_name);
        IDNumber = findViewById(R.id.input_number);
        birthDate = findViewById(R.id.input_date);
        RippleView saveBtn = findViewById(R.id.save_btn);

        birthDateLayout = findViewById(R.id.date_textinput_layout);
        legalNameInputLayout = findViewById(R.id.name_textinput_layout);
        IDNumberInputLayout = findViewById(R.id.number_textinput_layout);

        progressBar = findViewById(R.id.progress);

        setupToolbar();

        validator = new Validator(this);
        validator.setValidationListener(this);

        birthDate.setOnTouchListener((view, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                birthDate.setInputType(InputType.TYPE_NULL);
                startDatePicker();
            }
            return true;
        });

        saveBtn.setOnRippleCompleteListener(rippleView -> {
            validator.validate();
        });
    }

    private void setupToolbar() {
        toolbar.setTitle("Enter your credentials");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);
        }

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

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EnterCredentialsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        legalNameInputLayout.setError("");
        IDNumberInputLayout.setError("");
        birthDateLayout.setError("");

        String id_number_string = IDNumber.getText().toString().trim();
        String name = legalName.getText().toString().trim();
        String birth = birthDate.getText().toString().trim();

        int id_number = Integer.valueOf(id_number_string);

        Credentials credentials = new Credentials();
        credentials.setBirthDate(birth);
        credentials.setFullName(name);
        credentials.setNationalCard(id_number);

        CredentialsResponse creds = new CredentialsResponse();
        creds.setCredentials(credentials);

//        String json = new Gson().toJson(creds);

        progressBar.setVisibility(View.VISIBLE);
        koobaServerApi.postUpdateCredentials(creds)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<Response<Object>>() {

                    @Override
                    public void onNext(Response<Object> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.code() == 201) {
                            String responseJson = new Gson().toJson(response.body());
                            CredentialsResponse credentialsResponse =
                                    new Gson().fromJson(responseJson, CredentialsResponse.class);

                            sessionManager.setDetailsProvided(true);

                            MainActivity.startActivity(EnterCredentialsActivity.this);
                             finish();

                        } else {
                            Toast.makeText(EnterCredentialsActivity.this,
                                    "Sorry an error occurred", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Timber.e(t);
                        Toast.makeText(EnterCredentialsActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            int id = error.getView().getId();
            String message = error.getCollatedErrorMessage(this);

            switch (id) {
                case R.id.input_name:
                    legalNameInputLayout.setError(message);
                    break;
                case R.id.input_number:
                    IDNumberInputLayout.setError(message);
                    break;
                case R.id.input_date:
                    birthDateLayout.setError(message);
                default:
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
