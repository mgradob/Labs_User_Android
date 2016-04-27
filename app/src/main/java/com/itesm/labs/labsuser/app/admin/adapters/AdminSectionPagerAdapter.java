package com.itesm.labs.labsuser.app.admin.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;


/**
 * Created by mgradob on 6/8/15.
 */
public class AdminSectionPagerAdapter extends FragmentPagerAdapter {

    @Inject
    Context mContext;
    @Inject
    RequestsFragment mRequestsFragment;
    @Inject
    InventoryFragment mInventoryFragment;
    @Inject
    UsersFragment mUsersFragment;
    @Inject
    ReportsFragment mReportsFragment;

    public AdminSectionPagerAdapter(FragmentManager fm) {
        super(fm);
        LabsApp.get().inject(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mRequestsFragment;
            case 1:
                return mInventoryFragment;
            case 2:
                return mUsersFragment;
            case 3:
                return mReportsFragment;
            default:
                return mRequestsFragment;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.page_title_requests);
            case 1:
                return mContext.getString(R.string.page_title_inventory);
            case 2:
                return mContext.getString(R.string.page_title_users);
            case 3:
                return mContext.getString(R.string.page_title_reports);
            default:
                return mContext.getString(R.string.page_title_requests);
        }
    }
}
