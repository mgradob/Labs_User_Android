package com.itesm.labs.labsuser.activities.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CatAdapter;
import com.itesm.labs.labsuser.activities.adapters.ViewPagerAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloCat;
import com.itesm.labs.labsuser.activities.fragments.CategoriesFragment;
import com.itesm.labs.labsuser.activities.rest.models.Category;
import com.itesm.labs.labsuser.activities.tabs.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private String ENDPOINT, LAB_NAME;
    private int LAB_COLOR;
    private Toolbar mToolbar;
    private CategoriesFragment categoriesFragment;
    private ViewPager mPager;
    private ViewPagerAdapter mPagerAdapter;
    private SlidingTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ENDPOINT = getIntent().getStringExtra("ENDPOINT");
        LAB_NAME = getIntent().getStringExtra("LABNAME");
        LAB_COLOR = getIntent().getIntExtra("COLOR", getResources().getColor(R.color.primary));

        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        mToolbar.setBackgroundColor(LAB_COLOR);
        mToolbar.setTitle(LAB_NAME);
        setSupportActionBar(mToolbar);

        getWindow().setStatusBarColor(LAB_COLOR- 0x0f1216);

        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), ENDPOINT);

        mPager = (ViewPager) findViewById(R.id.activity_main_pager);
        mPager.setAdapter(mPagerAdapter);

        mTabLayout = (SlidingTabLayout) findViewById(R.id.activity_main_tabs);
        mTabLayout.setBackgroundColor(LAB_COLOR);
        mTabLayout.setDistributeEvenly(true);

        mTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        mTabLayout.setViewPager(mPager);
    }
}
