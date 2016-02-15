package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.FinishActivityEvent;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends LabsBaseActivity {

    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_main_pager)
    ViewPager mPager;
    @Bind(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    //region Stock
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupToolbar(mLabsPreferences.getLabColor(), mLabsPreferences.getCurrentLab().getName());

        setupStatusBar(mLabsPreferences.getLabColor());

        setupTabLayout();

        mEventBus.register(this);

//        startService(new Intent(mContext, BackgroundService.class));
    }

    @Override
    public void onBackPressed() {
        mEventBus.post(new BackPressedEvent());
    }
    //endregion

    //region Activity setup
    private void setupToolbar(int colorRes, String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            mToolbar.setBackgroundColor(getResources().getColor(colorRes, getTheme()));
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mToolbar.setBackgroundColor(getResources().getColor(colorRes));

        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }

    private void setupStatusBar(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().setStatusBarColor(getResources().getColor(colorRes, getTheme()));
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(colorRes));
    }

    private void setupTabLayout() {
        mPager.setAdapter(new AdminSectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);
    }
    //endregion

    //region Event Bus
    @Subscribe
    private void onFinishActivityEvent(FinishActivityEvent event){
        // TODO: 2/15/16 add logout login if needed.

        finish();
    }
    //endregion
}
