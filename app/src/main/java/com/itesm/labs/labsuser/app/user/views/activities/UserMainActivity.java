package com.itesm.labs.labsuser.app.user.views.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.services.NotificationService;
import com.itesm.labs.labsuser.app.commons.views.activities.AccountActivity;
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

        setupUi();

        mPager.setCurrentItem(1);

        startService(new Intent(mContext, NotificationService.class));
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
        mPager.setAdapter(new UserSectionPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setBackgroundColor(colorRes);
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
        }    }
}
