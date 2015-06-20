package com.itesm.labs.labsuser.activities.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CompAdapter;
import com.itesm.labs.labsuser.activities.rest.models.Cart;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.queries.ComponentQuery;

import java.util.ArrayList;

public class CategoriesDetailFragment extends Fragment {

    private Activity parentActivity;
    private ArrayList<Component> componentsList;
    private ListView mCategoryList;
    private SwipeRefreshLayout refreshLayout;

    private String ENDPOINT;
    private int CATEGORY_ID;
    private Cart userCart;

    public Activity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public int getCATEGORY_ID() {
        return CATEGORY_ID;
    }

    public void setCATEGORY_ID(int CATEGORY_ID) {
        this.CATEGORY_ID = CATEGORY_ID;
    }

    public Cart getUserCart() {
        return userCart;
    }

    public void setUserCart(Cart userCart) {
        this.userCart = userCart;
    }

    public CategoriesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_categories_detail_swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.material_blue, R.color.material_yellow, R.color.material_red);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        mCategoryList = (ListView) view.findViewById(R.id.fragment_categories_detail_listview);

        getCategoryComponents();
    }

    void getCategoryComponents() {
        refreshLayout.setRefreshing(true);

        new AsyncTask<String, Void, ArrayList<Component>>() {

            @Override
            protected ArrayList<Component> doInBackground(String... params) {
                ComponentQuery query = new ComponentQuery(params[0]);

                return query.getComponents(params[1]);
            }

            @Override
            protected void onPostExecute(ArrayList<Component> components) {
                super.onPostExecute(components);

                if (components != null) {
                    componentsList = components;

                    mCategoryList.setAdapter(new CompAdapter(getActivity().getApplicationContext(),
                            componentsList, userCart));

                    refreshLayout.setRefreshing(false);
                }
            }
        }.execute(ENDPOINT, "" + CATEGORY_ID);
    }

    private void refreshList() {
        getCategoryComponents();
    }
}
