package com.invictus.nkoba.nkoba.di.module.fragment_module;

import com.invictus.nkoba.nkoba.ui.fragments.LoanReturnFragment;
import com.invictus.nkoba.nkoba.ui.fragments.RequestLoanFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by invictus on 5/4/18.
 */
@Module
public abstract class MainFragmentModule {
    @ContributesAndroidInjector
    abstract RequestLoanFragment contributeRequestLoanFragment();

    @ContributesAndroidInjector
    abstract LoanReturnFragment contributeLoanReturnFragment();
}
