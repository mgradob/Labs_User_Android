package com.itesm.labs.labsuser.app.commons.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.itesm.labs.labsuser.app.commons.views.presenters.MainActivityPresenter;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_main_pager)
    ViewPager mPager;
    @Bind(R.id.activity_main_tabs)
    TabLayout mTabLayout;

    MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new MainActivityPresenter(this);
        mPresenter.setupNfc();
        mPresenter.enableNfc();

        setupUi();

//        startService(new Intent(mContext, BackgroundService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_account:
                startActivity(new Intent(mContext, AccountActivity.class));
                return true;
            case R.id.menu_main_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.disableNfc();
    }

    @Override
    public void setupUi() {
        setupToolbar(mLabsPreferences.getLabColor(), mLabsPreferences.getCurrentLab().getName());

        setupStatusBar(mLabsPreferences.getLabColor());

        setupTabLayout(mLabsPreferences.getLabColor());
    }

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

    @Subscribe
    public void onUIDEvent(UIDEvent event) {
        if (event != null)
            Log.d("Test", "UID: " + event.getUID());
    }
}
