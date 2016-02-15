package com.itesm.labs.labsuser.app.admin.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.AllInventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsMainFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersFragment;

import javax.inject.Inject;


/**
 * Created by mgradob on 6/8/15.
 */
public class AdminSectionPagerAdapter extends FragmentPagerAdapter {

    @Inject
    Context mContext;
    @Inject
    RequestsMainFragment mRequestsMainFragment;
    @Inject
    AllInventoryFragment mAllInventoryFragment;
    @Inject
    UsersFragment mUsersFragment;
    @Inject
    ReportsFragment mReportsFragment;

    public AdminSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mRequestsMainFragment;
            case 1:
                return mAllInventoryFragment;
            case 2:
                return mUsersFragment;
            case 3:
                return mReportsFragment;
            default:
                return mRequestsMainFragment;
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
