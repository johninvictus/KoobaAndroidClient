package com.invictus.nkoba.nkoba.ui.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.activities.MainActivity;
import com.invictus.nkoba.nkoba.ui.fragments.LoanReturnFragment;
import com.invictus.nkoba.nkoba.ui.fragments.RequestLoanFragment;

import javax.inject.Inject;

/**
 * Created by invictus on 5/3/18.
 */

public class MainNavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public MainNavigationController(MainActivity activity) {
        containerId = R.id.fragment_container;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void navigateToRequestFragment() {

        Fragment fragment = new RequestLoanFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToLoanPaymentFragment() {
        Fragment fragment = new LoanReturnFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }
}
