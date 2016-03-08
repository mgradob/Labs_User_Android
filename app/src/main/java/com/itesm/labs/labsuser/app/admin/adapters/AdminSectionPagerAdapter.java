package com.itesm.labs.labsuser.app.admin.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersFragment;
import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;


/**
 * Created by mgradob on 6/8/15.
 */
public class AdminSectionPagerAdapter extends FragmentPagerAdapter {

    @Inject
    Context mContext;
    @Inject
    RequestsControllerFragment mRequestsControllerFragment;
    @Inject
    InventoryControllerFragment mInventoryControllerFragment;
    @Inject
    UsersControllerFragment mUsersControllerFragment;
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
                return mRequestsControllerFragment;
            case 1:
                return mInventoryControllerFragment;
            case 2:
                return mUsersControllerFragment;
            case 3:
                return mReportsFragment;
            default:
                return mRequestsControllerFragment;
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
