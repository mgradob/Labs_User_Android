package com.itesm.labs.labsuser.app.user.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.user.views.fragments.UserCartFragment;
import com.itesm.labs.labsuser.app.user.views.fragments.UserHistoryFragment;
import com.itesm.labs.labsuser.app.user.views.fragments.UserInventoryFragment;

import javax.inject.Inject;


/**
 * Created by mgradob on 6/8/15.
 */
public class UserSectionPagerAdapter extends FragmentPagerAdapter {

    @Inject
    Context mContext;
    @Inject
    UserInventoryFragment mUserInventoryFragment;
    @Inject
    UserCartFragment mUserCartFragment;
    @Inject
    UserHistoryFragment mUserHistoryFragment;

    public UserSectionPagerAdapter(FragmentManager fm) {
        super(fm);
        LabsApp.get().inject(this);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mUserInventoryFragment;
            case 1:
                return mUserCartFragment;
            case 2:
                return mUserHistoryFragment;
            default:
                return mUserInventoryFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.page_title_categories);
            case 1:
                return mContext.getString(R.string.page_title_cart);
            case 2:
                return mContext.getString(R.string.page_title_history);
            default:
                return mContext.getString(R.string.page_title_categories);
        }
    }
}
