package com.itesm.labs.labsuser.app.user.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.user.adapters.UserSectionPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserMainActivity extends BaseActivity {

    private static final String TAG = UserMainActivity.class.getSimpleName();

    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_main_pager)
    ViewPager mPager;
    @Bind(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupToolbar();

        setupStatusBar();

        setupTabLayout();

        mPager.setCurrentItem(1);

//        startService(new Intent(mContext, BackgroundService.class));
    }

    @Override
    public void setupUi() {
        setupToolbar();
        setupStatusBar();
        setupTabLayout();
    }

    private void setupToolbar() {
        mToolbar.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getLabColor()]);
        mToolbar.setTitle(mLabsPreferences.getCurrentLab().getName());
        setSupportActionBar(mToolbar);
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getIntArray(R.array.material_colors_dark)[mLabsPreferences.getLabColor()]);
    }

    private void setupTabLayout() {
        mPager.setAdapter(new UserSectionPagerAdapter(getSupportFragmentManager()));

        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getLabColor()]);
        mTabLayout.setupWithViewPager(mPager);
    }
}
