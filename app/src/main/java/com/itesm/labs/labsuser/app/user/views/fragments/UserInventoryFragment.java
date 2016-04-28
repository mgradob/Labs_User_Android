package com.itesm.labs.labsuser.app.user.views.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.itesm.labs.labsuser.app.user.adapters.UserCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserInventoryPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInventoryFragment extends BaseFragment implements IListContract {

    private static final String TAG = UserInventoryFragment.class.getSimpleName();

    @Bind(R.id.fragment_categories_grid_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_categories_grid_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Inject
    LabsPreferences mLabsPreferences;

    UserInventoryPresenter mPresenter;

    private UserCategoryRecyclerAdapter mCategoriesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new UserInventoryPresenter(this);
        mPresenter.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_materials, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();
    }

    @Override
    protected void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    protected void showError(ErrorType error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setupList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new UserCategoryRecyclerAdapter(getActivity());

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
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
        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.getCategories();
        });
    }

    @Override
    public void updateInfo(List data) {
        refreshLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }
}
