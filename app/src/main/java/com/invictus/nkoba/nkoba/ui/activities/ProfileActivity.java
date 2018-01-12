package com.invictus.nkoba.nkoba.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.invictus.nkoba.nkoba.R;
import com.invictus.nkoba.nkoba.ui.fragments.LoanHistoryFragment;
import com.invictus.nkoba.nkoba.ui.fragments.UserDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by invictus on 1/9/18.
 */

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabs = findViewById(R.id.tabs);

        setUpToolbar();
        setupViewPager();
    }

    private void setUpToolbar() {
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserDetailsFragment(), "Account");
        adapter.addFragment(new LoanHistoryFragment(), "Loan History");
        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            mFragmentList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
