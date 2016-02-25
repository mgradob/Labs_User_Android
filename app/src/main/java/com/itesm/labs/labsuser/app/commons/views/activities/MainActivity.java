package com.itesm.labs.labsuser.app.commons.views.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.DismissDialogEvent;
import com.itesm.labs.labsuser.app.commons.events.ShowDialogEvent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

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

        setupUi();
//        startService(new Intent(mContext, BackgroundService.class));
    }

    @Override
    public void setupUi() {
        setupToolbar(mLabsPreferences.getLabColor(), mLabsPreferences.getCurrentLab().getName());

        setupStatusBar(mLabsPreferences.getLabColor());

        setupTabLayout(mLabsPreferences.getLabColor());
    }

    @Override
    public void onBackPressed() {
        mEventBus.post(new BackPressedEvent());
    }
    //endregion

    //region Activity setup
    private void setupToolbar(int colorRes, String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mToolbar.setBackgroundColor(colorRes);

        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
    }

    private void setupTabLayout(int colorRes) {
        mPager.setAdapter(new AdminSectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setBackgroundColor(colorRes);
    }
    //endregion

    //region Event Bus
    @Override
    public void onShowDialogEvent(ShowDialogEvent event) {
    }

    @Override
    public void onDismissDialogEvent(DismissDialogEvent event) {
    }
    //endregion
}
