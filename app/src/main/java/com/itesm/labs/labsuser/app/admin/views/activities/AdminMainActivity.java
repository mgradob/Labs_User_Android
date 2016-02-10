package com.itesm.labs.labsuser.app.admin.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.commons.services.BackgroundService;
import com.itesm.labs.labsuser.app.admin.views.fragments.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.views.common.LabsBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdminMainActivity extends LabsBaseActivity {

    private static final String TAG = AdminMainActivity.class.getSimpleName();

    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_main_pager)
    ViewPager mPager;
    @Bind(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    private RequestsFragment mRequestsFragment = new RequestsFragment();
    private InventoryFragment mInventoryFragment = new InventoryFragment();
    private ReportsFragment mReportsFragment = new ReportsFragment();
    private UsersFragment mUsersFragment = new UsersFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupToolbar();

        setupStatusBar();

        setupTabLayout();

        Intent callingIntent = getIntent();
        if (callingIntent.getBooleanExtra(AppConstants.NOTIF_EXTRA_USER, false))
            mPager.setCurrentItem(1);

        startService(new Intent(mContext, BackgroundService.class));
    }

    private void setupToolbar() {
        mToolbar.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getCurrentLab().getColorResource()]);
        mToolbar.setTitle(mLabsPreferences.getCurrentLab().getName());
        setSupportActionBar(mToolbar);
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getIntArray(R.array.material_colors_dark)[mLabsPreferences.getCurrentLab().getColorResource()]);
    }

    private void setupTabLayout() {
        mPager.setAdapter(new AdminSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext(), mLabsPreferences.getAppFlow()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mRequestsFragment;
                    case 1:
                        return mInventoryFragment;
                    case 2:
                        return mReportsFragment;
                    case 3:
                        return mUsersFragment;
                    default:
                        return mRequestsFragment;
                }
            }
        });

        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getCurrentLab().getColorResource()]);
        mTabLayout.setupWithViewPager(mPager);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
