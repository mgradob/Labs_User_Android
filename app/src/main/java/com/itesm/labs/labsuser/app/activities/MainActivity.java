package com.itesm.labs.labsuser.app.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.fragments.CartFragment;
import com.itesm.labs.labsuser.app.fragments.MaterialsFragment;
import com.itesm.labs.labsuser.app.fragments.RecordFragment;
import com.itesm.labs.labsuser.app.tabs.SectionPagerAdapter;
import com.itesm.labs.labsuser.app.utils.SnackbarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends LabsBaseActivity implements SnackbarUtil {

    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    @Bind(R.id.activity_main_pager)
    ViewPager mPager;

    @Bind(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    private MaterialsFragment materialsFragment = new MaterialsFragment();
    private CartFragment cartFragment = new CartFragment();
    private RecordFragment recordFragment = new RecordFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupToolbar();

        setupStatusBar();

        setupTabLayout();

        Intent callingIntent = getIntent();
        if (callingIntent.getBooleanExtra(getResources().getString(R.string.notification_intent_action), false))
            mPager.setCurrentItem(1);
    }

    private void setupToolbar() {
        mToolbar.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mAppGlobals.getLaboratory().getColorResource()]);
        mToolbar.setTitle(mAppGlobals.getLaboratory().getName());
        setSupportActionBar(mToolbar);
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getIntArray(R.array.material_colors_dark)[mAppGlobals.getLaboratory().getColorResource()]);
    }

    private void setupTabLayout() {
        mPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager(), getApplicationContext()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return materialsFragment;
                    case 1:
                        return cartFragment;
                    case 2:
                        return recordFragment;
                    default:
                        return materialsFragment;
                }
            }
        });

        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mAppGlobals.getLaboratory().getColorResource()]);
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
        if (mPager.getCurrentItem() == 0)
            if (materialsFragment.isInCategoryDetails)
                materialsFragment.returnToCategories();
            else
                finish();
        else
            super.onBackPressed();
    }

    @Override
    public void showSnackbar(String stringResource) {
    }

    @Override
    public void showSnackbarWithCallback(String stringResource, Snackbar.Callback callback) {

    }
}
