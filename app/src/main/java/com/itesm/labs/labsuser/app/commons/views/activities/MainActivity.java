package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.events.DialogDismissEvent;
import com.itesm.labs.labsuser.app.commons.events.DialogShowEvent;
import com.itesm.labs.labsuser.app.commons.events.SnackbarEvent;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

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
//        startService(new Intent(mContext, BackgroundService.class));
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

    @Override
    public void onShowDialogEvent(DialogShowEvent event) {

    }

    @Override
    public void onDismissDialogEvent(DialogDismissEvent event) {

    }

    @Subscribe
    public void onSnackbarEvent(SnackbarEvent event) {
        if (event != null)
            Snackbar.make(findViewById(R.id.activity_main), event.getBodyRes(), Snackbar.LENGTH_LONG)
                    .show();
    }
}
