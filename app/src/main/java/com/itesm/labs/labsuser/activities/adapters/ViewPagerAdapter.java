package com.itesm.labs.labsuser.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.itesm.labs.labsuser.activities.fragments.CartFragment;
import com.itesm.labs.labsuser.activities.fragments.CategoriesFragment;
import com.itesm.labs.labsuser.activities.fragments.RecordFragment;

/**
 * Created by mgradob on 3/8/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final Integer NUM_OF_TABS = 3;
    private CharSequence tabs[] = {"Categorias", "Carrito", "Historial"};
    private String ENDPOINT;

    public ViewPagerAdapter(FragmentManager fm, String ENDPOINT) {
        super(fm);
        this.ENDPOINT = ENDPOINT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CategoriesFragment categoriesFragment = new CategoriesFragment();
                categoriesFragment.setENDPOINT(ENDPOINT);
                return categoriesFragment;
            case 1:
                CartFragment cartFragment = new CartFragment();
                cartFragment.setENDPOINT(ENDPOINT);
                return cartFragment;
            case 2:
                RecordFragment recordFragment = new RecordFragment();
                recordFragment.setENDPOINT(ENDPOINT);
                return recordFragment;
            default:
                CategoriesFragment defaultFragment = new CategoriesFragment();
                defaultFragment.setENDPOINT(ENDPOINT);
                return defaultFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return NUM_OF_TABS;
    }
}
