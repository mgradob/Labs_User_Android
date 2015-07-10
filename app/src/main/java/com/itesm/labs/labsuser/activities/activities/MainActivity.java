package com.itesm.labs.labsuser.activities.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.fragments.CartFragment;
import com.itesm.labs.labsuser.activities.fragments.CategoriesDetailFragment;
import com.itesm.labs.labsuser.activities.fragments.CategoriesFragment;
import com.itesm.labs.labsuser.activities.fragments.CategoriesGridFragment;
import com.itesm.labs.labsuser.activities.fragments.RecordFragment;
import com.itesm.labs.labsuser.activities.rest.models.Cart;
import com.itesm.labs.labsuser.activities.rest.models.CartItem;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.queries.CartQuery;
import com.itesm.labs.labsuser.activities.rest.queries.ComponentQuery;
import com.itesm.labs.labsuser.activities.sqlite.LabsUserSqliteOpenHelper;
import com.itesm.labs.labsuser.activities.tabs.SectionPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements CategoriesGridFragment.CatGridFragInteractionListener,
        CartFragment.CartFragInteractionListener {

    private String ENDPOINT, LAB_NAME, USER_ID;
    private int LAB_COLOR;
    private Toolbar mToolbar;
    private ViewPager mPager;
    private TabLayout mTabLayout;

    private Cart userCart;
    private CategoriesFragment categoriesFragment, defaultFragment;
    private CartFragment cartFragment;
    private RecordFragment recordFragment;
    private LabsUserSqliteOpenHelper labsUserSqliteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ENDPOINT = getIntent().getStringExtra("ENDPOINT");
        LAB_NAME = getIntent().getStringExtra("LABNAME");
        LAB_COLOR = getIntent().getIntExtra("COLOR", getResources().getColor(R.color.primary));
        USER_ID = getIntent().getStringExtra("USERID");

        mToolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        mToolbar.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[LAB_COLOR]);
        mToolbar.setTitle(LAB_NAME);
        setSupportActionBar(mToolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getIntArray(R.array.material_colors_dark)[LAB_COLOR]);

        userCart = new Cart();

        mPager = (ViewPager) findViewById(R.id.activity_main_pager);
        mPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        categoriesFragment = new CategoriesFragment();
                        categoriesFragment.setENDPOINT(ENDPOINT);
                        return categoriesFragment;
                    case 1:
                        cartFragment = new CartFragment();
                        cartFragment.setENDPOINT(ENDPOINT);
                        cartFragment.setUSER_ID(USER_ID);
                        return cartFragment;
                    case 2:
                        recordFragment = new RecordFragment();
                        recordFragment.setENDPOINT(ENDPOINT);
                        recordFragment.setParentActivity(MainActivity.this);
                        recordFragment.refreshList();
                        return recordFragment;
                    default:
                        defaultFragment = new CategoriesFragment();
                        defaultFragment.setENDPOINT(ENDPOINT);
                        return defaultFragment;
                }
            }
        });

        mTabLayout = (TabLayout) findViewById(R.id.activity_main_tabs);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setBackgroundColor(getResources().getIntArray(R.array.material_colors)[LAB_COLOR]);
        mTabLayout.setTabTextColors(R.color.primary_text_light, R.color.secondary_text_light);

        labsUserSqliteOpenHelper = new LabsUserSqliteOpenHelper(getApplicationContext());
        labsUserSqliteOpenHelper.getReadableDatabase();

        if (findViewById(R.id.activity_main) != null) {
            if (savedInstanceState != null) return;

            categoriesFragment = new CategoriesFragment();
            categoriesFragment.setENDPOINT(ENDPOINT);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                    .replace(R.id.fragment_categories_container, categoriesFragment)
                    .commit();
        }

        initCart();
    }

    private void initCart() {
        final ProgressDialog dialog = ProgressDialog
                .show(this, "Espere...", "Obteniendo datos", true);

        userCart.setUserId(USER_ID);
        try {
            new AsyncTask<String, Void, ArrayList<CartItem>>() {
                @Override
                protected ArrayList<CartItem> doInBackground(String... params) {
                    ArrayList<CartItem> tempList;
                    try {
                        tempList = new CartQuery(params[0]).getCartOf(USER_ID);
                    } catch (Exception ex) {
                        return null;
                    }

                    try {
                        for (CartItem item : tempList) {
                            Component tempComponent = new ComponentQuery(params[0])
                                    .getComponent(item.getComponentId());
                            item.setComponentName(tempComponent.getName());
                            item.setComponentNote(tempComponent.getNote());
                        }
                    } catch (Exception ex) {
                        return null;
                    }

                    return tempList;
                }

                @Override
                protected void onPostExecute(ArrayList<CartItem> cartItems) {
                    super.onPostExecute(cartItems);

                    userCart.setCartList(cartItems);

                    if (categoriesFragment != null)
                        categoriesFragment.setUserCart(userCart);
                    if (cartFragment != null) {
                        cartFragment.setUserCart(userCart);
                        cartFragment.refreshList();
                    }

                    dialog.dismiss();
                }
            }.execute(ENDPOINT);
        } catch (Exception ex) {

        }
    }

    @Override
    public void displayCategory(int categoryId) {
        CategoriesDetailFragment categoriesDetailFragment = new CategoriesDetailFragment();
        categoriesDetailFragment.setParentActivity(this);
        categoriesDetailFragment.setENDPOINT(ENDPOINT);
        categoriesDetailFragment.setCATEGORY_ID(categoryId);
        categoriesDetailFragment.setUserCart(userCart);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("categoriesFragment")
                .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                .replace(R.id.fragment_categories_container, categoriesDetailFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void refreshUserCart() {
    }
}
