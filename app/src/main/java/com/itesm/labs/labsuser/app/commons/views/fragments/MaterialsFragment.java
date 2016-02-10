package com.itesm.labs.labsuser.app.commons.views.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.commons.adapters.CategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.ComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.rest.clients.CategoryService;
import com.itesm.labs.labsuser.app.rest.clients.ComponentService;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MaterialsFragment extends LabsBaseFragment {

    private static final String TAG = MaterialsFragment.class.getSimpleName();

    @Bind(R.id.fragment_categories_grid_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_categories_grid_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Inject
    AppGlobals mAppGlobals;
    @Inject
    CategoryService mCategoryService;
    @Inject
    ComponentService mComponentService;
    @Inject
    SharedPreferences mSharedPreferences;

    private ArrayList<Category> categoryList = new ArrayList<>();
    private ArrayList<Component> componentArrayList = new ArrayList<>();

    private CategoryRecyclerAdapter mCategoriesAdapter;
    private ComponentRecyclerAdapter mComponentsAdapter;

    public boolean isInCategoryDetails = false;

    public MaterialsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isInCategoryDetails = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories_grid, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRefreshLayout();

        setupCategoriesRecyclerView();

        refreshCategoriesList();
    }

    private void setupRefreshLayout() {
        refreshLayout.setColorSchemeResources(
                R.color.material_red,
                R.color.material_pink,
                R.color.material_deep_purple,
                R.color.material_indigo,
                R.color.material_blue,
                R.color.material_light_blue,
                R.color.material_cyan,
                R.color.material_teal,
                R.color.material_green,
                R.color.material_light_green,
                R.color.material_yellow,
                R.color.material_amber,
                R.color.material_deep_orange,
                R.color.material_brown,
                R.color.material_grey
        );
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInCategoryDetails)
                    refreshCategoriesList();
                else
                    refreshCategoryDetails();
            }
        });
    }

    private void setupCategoriesRecyclerView() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mRecyclerView.setHasFixedSize(true);

        if (mCategoriesAdapter == null) {
            mCategoriesAdapter = new CategoryRecyclerAdapter();
            mCategoriesAdapter.setOnClickListener(new BaseRecyclerAdapter.IOnClickListener() {
                @Override
                public void onItemClick(int position) {
                    mAppGlobals.setCategory(categoryList.get(position));
                    goToCategoryDetails();
                }
            });
        }

        mRecyclerView.setAdapter(mCategoriesAdapter);
    }

    private void setupComponentsRecyclerView() {
        isInCategoryDetails = true;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRecyclerView.setHasFixedSize(true);

        if (mComponentsAdapter == null) {
            mComponentsAdapter = new ComponentRecyclerAdapter();
        }

        mRecyclerView.setAdapter(mComponentsAdapter);
    }

    public void returnToCategories() {
        isInCategoryDetails = false;
        setupCategoriesRecyclerView();
        refreshCategoriesList();
    }

    private void goToCategoryDetails() {
        isInCategoryDetails = true;
        setupComponentsRecyclerView();
        refreshCategoryDetails();
    }

    private void refreshCategoriesList() {
        mCategoryService.getCategories(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Category>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get categories started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get categories completed");
                        refreshLayout.setRefreshing(false);

                        mCategoriesAdapter.refresh(categoryList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get categories error : " + e.getMessage());

                        refreshLayout.setRefreshing(false);

                        Snackbar.make(getView(),
                                getResources().getString(R.string.categories_grid_snackbar_error_content),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(ArrayList<Category> categories) {
                        if (categories == null)
                            throw new NullPointerException("Categories are null");

                        categoryList = categories;
                    }
                });
    }

    private void refreshCategoryDetails() {
        mComponentService.getAllCategoryComponents(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(),
                mAppGlobals.getCategory().getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Component>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get components started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get components completed");
                        refreshLayout.setRefreshing(false);

                        mComponentsAdapter.refresh(componentArrayList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get components error: " + e.getMessage());

                        refreshLayout.setRefreshing(false);

                        Snackbar.make(getView(),
                                getResources().getString(R.string.categories_detail_snackbar_error_content),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(ArrayList<Component> components) {
                        if (components == null)
                            throw new NullPointerException("Components are null");

                        componentArrayList = components;
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
