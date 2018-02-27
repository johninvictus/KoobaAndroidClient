package com.invictus.nkoba.nkoba.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.invictus.nkoba.nkoba.R;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by invictus on 1/5/18.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static final int APP_REQUEST_CODE = 1;
    private static final int AUTH_ACTIVITY = 2;
    RippleView rippleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        rippleView = findViewById(R.id.login_register_btn);
        rippleView.setOnRippleCompleteListener(rippleView1 -> onLogin(LoginType.PHONE));
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WelcomeActivity.class));
    }

    private void onLogin(final LoginType loginType) {
        Intent intent = new Intent(this, AccountKitActivity.class);

        // use AccountKitActivity.ResponseType.CODE to handle via your server
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.CODE);

        final AccountKitConfiguration configuration = configurationBuilder.build();

        // launch the account kit activity
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);

        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (loginResult.getError() != null) {
                String errorStr = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, errorStr, Toast.LENGTH_SHORT).show();
            } else if (loginResult.wasCancelled()) {
                String toastMessage = "Login Canceled";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            } else {
                String authCode = loginResult.getAuthorizationCode();

                Intent intent = new Intent(this, AuthActivity.class);
                intent.putExtra(AuthActivity.AUTH_EXTRA, authCode);


                startActivityForResult(intent, AUTH_ACTIVITY);

                //send this token to my server
                Timber.d(authCode);
            }
        }else if (requestCode == AUTH_ACTIVITY){
            switch (data.getAction()){
                case AuthActivity.AUTH_ERROR:
                    Toast.makeText(WelcomeActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                    break;
                case AuthActivity.AUTH_SUCCESS:
                    Toast.makeText(WelcomeActivity.this, "All is good", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // client authorization
    private void afterLogin() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                // grab all your data
                // #get account id
                String accountId = account.getId();

                // number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String number = formattedNumber(phoneNumber.toString());
                } else {
                    // use the email
                }
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                //show error toast
                // send data to crashlytics
                String errorString = accountKitError.getErrorType().getMessage();
                Toast.makeText(WelcomeActivity.this, errorString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formattedNumber(String phoneNumber) {

        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse(phoneNumber, Locale.getDefault().getCountry());

            phoneNumber = pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        return phoneNumber;
    }
}
