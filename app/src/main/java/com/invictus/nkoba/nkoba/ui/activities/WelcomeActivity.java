package com.invictus.nkoba.nkoba.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andexert.library.RippleView;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.invictus.nkoba.nkoba.R;

/**
 * Created by invictus on 1/5/18.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static final int APP_REQUEST_CODE = 1;
    RippleView rippleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        rippleView = findViewById(R.id.login_register_btn);
        rippleView.setOnRippleCompleteListener(rippleView1 -> onLogin(LoginType.EMAIL));
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
                        AccountKitActivity.ResponseType.TOKEN);

        final AccountKitConfiguration configuration = configurationBuilder.build();

        // launch the account kit activity
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration);

        startActivityForResult(intent, APP_REQUEST_CODE);
    }
}
