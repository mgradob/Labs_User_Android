//package com.itesm.labs.labsuser.app.user.views.activities;
//
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.Toolbar;
//
//import com.itesm.labs.labsuser.R;
//import com.itesm.labs.labsuser.app.application.AppConstants;
//import com.itesm.labs.labsuser.app.commons.services.BackgroundService;
//import com.itesm.labs.labsuser.app.views.common.LabsBaseActivity;
//import com.itesm.labs.labsuser.app.user.views.fragments.CartFragment;
//import com.itesm.labs.labsuser.app.user.views.fragments.MaterialsFragment;
//import com.itesm.labs.labsuser.app.user.views.fragments.HistoryFragment;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//public class UserMainActivity extends LabsBaseActivity {
//
//    private static final String TAG = UserMainActivity.class.getSimpleName();
//
//    @Bind(R.id.activity_main_toolbar)
//    Toolbar mToolbar;
//    @Bind(R.id.activity_main_pager)
//    ViewPager mPager;
//    @Bind(R.id.activity_main_tabs)
//    TabLayout mTabLayout;
//
//    private MaterialsFragment materialsFragment = new MaterialsFragment();
//    private CartFragment cartFragment = new CartFragment();
//    private HistoryFragment historyFragment = new HistoryFragment();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        ButterKnife.bind(this);
//
//        setupToolbar();
//
//        setupStatusBar();
//
//        setupTabLayout();
//
//        Intent callingIntent = getIntent();
//        if (callingIntent.getBooleanExtra(AppConstants.NOTIF_EXTRA_USER, false))
//            mPager.setCurrentItem(1);
//
//        startService(new Intent(mContext, BackgroundService.class));
//    }
//
//    private void setupToolbar() {
//        mToolbar.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getCurrentLab().getColorResource()]);
//        mToolbar.setTitle(mLabsPreferences.getCurrentLab().getName());
//        setSupportActionBar(mToolbar);
//    }
//
//    private void setupStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            getWindow().setStatusBarColor(getResources().getIntArray(R.array.material_colors_dark)[mLabsPreferences.getCurrentLab().getColorResource()]);
//    }
//
//    private void setupTabLayout() {
//        mPager.setAdapter(new AdminSectionPagerAdapter(getSupportFragmentManager(), getApplicationContext(), mLabsPreferences.getAppFlow()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position) {
//                    case 0:
//                        return materialsFragment;
//                    case 1:
//                        return cartFragment;
//                    case 2:
//                        return historyFragment;
//                    default:
//                        return materialsFragment;
//                }
//            }
//        });
//
//        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[mLabsPreferences.getCurrentLab().getColorResource()]);
//        mTabLayout.setupWithViewPager(mPager);
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0)
//            if (materialsFragment.isInCategoryDetails)
//                materialsFragment.returnToCategories();
//            else
//                finish();
//        else
//            super.onBackPressed();
//    }
//}
