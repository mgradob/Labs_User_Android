package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.user.adapters.CategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.rest.clients.ApiService;
import com.mgb.labsapi.models.Category;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class InventoryFragment extends LabsBaseFragment {

    private final String TAG = InventoryFragment.class.getSimpleName();

    @Bind(R.id.fragment_inventory_progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.fragment_inventory_categories_list)
    RecyclerView mCategoriesListView;

    @Bind(R.id.fragment_inventory_subtoolbar)
    Toolbar mSubtoolbar;

    @Bind(R.id.fragment_inventory_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    ApiService mApiService;

    private CategoryRecyclerAdapter mAdapter;

    private ArrayList<Category> mCategoriesData = new ArrayList<>();

    public InventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCategoriesList();

        setupToolbar();

        setupRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        getCategoriesInfo();
    }

    private void setupCategoriesList() {
        mAdapter = new CategoryRecyclerAdapter();
        mCategoriesListView.setAdapter(mAdapter);
    }

    private void setupToolbar() {
        mSubtoolbar.setTitle("Inventario");
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategoriesInfo();
            }
        });
    }

    @OnItemClick(R.id.fragment_inventory_categories_list)
    void onItemClick(int position) {
        // TODO: 12/27/15 replace
//        Intent intent = new Intent(mContext, InventoryDetailActivity.class);
//        intent.putExtra("CATEGORYID", mCategoriesData.get(position).getId());
//        intent.putExtra("CATEGORYTITLE", mCategoriesData.get(position).getName());
//        intent.putExtra("CATEGORYICON", mCategoriesData.get(position).getImageResource());
//        startActivity(intent);
    }

    @OnItemLongClick(R.id.fragment_inventory_categories_list)
    void onItemLongClick(int position) {
        // TODO: 12/27/15 replace
//        Intent intent = new Intent(mContext, AddCategoryActivity.class);
//        intent.putExtra("INDEX", mCategoriesData.get(position).getId());
//        intent.putExtra("CATEGORYNAME", mCategoriesData.get(position).getName());
//        intent.putExtra("ISEDIT", true);
//        startActivityForResult(intent, ADD_CATEGORY_REQUEST);
//
//        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getCategoriesInfo();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categories, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                // TODO: 12/27/15 replace
//                Intent intent = new Intent(mContext, AddCategoryActivity.class);
//                intent.putExtra("INDEX", mCategoriesData.size());
//                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void progressBarEvent(boolean show) {
        if (show) {
            mProgressBar.setIndeterminate(true);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setIndeterminate(false);
            mProgressBar.setVisibility(View.INVISIBLE);

            mRefreshLayout.setRefreshing(false);
        }
    }

    private void getCategoriesInfo() {
        mApiService.getCategoriesFromDb()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Category>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get categories started");
                        progressBarEvent(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get categories completed");
                        progressBarEvent(false);

                        mAdapter.refresh(mCategoriesData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get categories error");
                        progressBarEvent(false);

                        // TODO: 11/5/15 add snackbar
                    }

                    @Override
                    public void onNext(ArrayList<Category> categories) {
                        if (categories == null)
                            throw new NullPointerException("Categories is null");

                        mCategoriesData = categories;
                    }
                });
    }
}
