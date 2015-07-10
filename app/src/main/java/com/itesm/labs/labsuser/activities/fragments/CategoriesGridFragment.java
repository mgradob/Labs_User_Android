package com.itesm.labs.labsuser.activities.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CategoriesRecyclerAdapter;
import com.itesm.labs.labsuser.activities.async_tasks.HiloCat;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

public class CategoriesGridFragment extends Fragment {

    private CatGridFragInteractionListener catGridFragInteractionListener;
    private RecyclerView mRecyclerView;
    private CategoriesRecyclerAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;

    private String ENDPOINT;
    private ArrayList<Category> listaCat;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public CategoriesGridFragment() {
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
        return inflater.inflate(R.layout.fragment_categories_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_categories_grid_swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_categories_grid_recyclerview);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new CategoriesRecyclerAdapter();
        mAdapter.setmContext(getActivity().getApplicationContext());
        mAdapter.setMyOnClickListener(new CategoriesRecyclerAdapter.MyOnClickListener() {
            @Override
            public void mOnClick(View v, int position) {
                catGridFragInteractionListener.displayCategory(listaCat.get(position).getId());
            }
        });

        new HiloCat() {
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                if (categories != null) {
                    listaCat = categories;

                    mAdapter.setmCategories(listaCat);

                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }.execute(ENDPOINT);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            catGridFragInteractionListener = (CatGridFragInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CatGridFragInteractionListener");
        }
    }

    private void refreshList() {
        new HiloCat() {
            @Override
            protected void onPostExecute(ArrayList<Category> categories) {
                super.onPostExecute(categories);

                if (categories != null) {
                    listaCat = categories;

                    mAdapter.setmCategories(listaCat);

                    refreshLayout.setRefreshing(false);
                }
            }
        }.execute(ENDPOINT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ENDPOINT", ENDPOINT);
    }

    /**
     * Interface para comunicarse con actividad superior y otros fragmentos.
     */
    public interface CatGridFragInteractionListener {
        void displayCategory(int categoryId);
    }
}
