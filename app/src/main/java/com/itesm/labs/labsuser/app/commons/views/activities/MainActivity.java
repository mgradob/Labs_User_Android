package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsPresenter;
import com.itesm.labs.labsuser.app.bases.BasePresenter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends LabsBaseActivity implements
        BasePresenter, RequestsPresenter {

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

        setupToolbar(mLabsPreferences.getLabColor(), mLabsPreferences.getCurrentLab().getName());

        setupStatusBar(mLabsPreferences.getLabColor());

        setupTabLayout();

        getDataFromLab();

//        startService(new Intent(mContext, BackgroundService.class));
    }

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
//        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mAppGlobals.getLaboratory().getColorResource()]);
        mTabLayout.setupWithViewPager(mPager);
    }
    //endregion

    //region Data sync
    private void getDataFromLab(){

    }
    //endregion

    //region Interfaces
    @Override
    public void updateToolbar(int colorRes, String title) {
        setupStatusBar(colorRes);
        setupToolbar(colorRes, title);
    }

    @Override
    public void showSnackbar(int stringRes) {
        Snackbar.make(findViewById(R.id.activity_main), stringRes, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showDialog(int titleResource, int contentResource) {
        showMaterialDialog(titleResource, contentResource);
    }

    @Override
    public void dismissDialog() {
        dismissMaterialDialog();
    }
    //endregion
}
