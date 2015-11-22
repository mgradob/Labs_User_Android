package com.itesm.labs.labsuser.app.tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itesm.labs.labsuser.R;


/**
 * Created by mgradob on 6/8/15.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
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
