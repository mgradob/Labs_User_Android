package com.itesm.labs.labsuser.app.admin.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 2/12/16.
 */
public class RequestsFragment extends BaseFragment implements IListContract, AdminRequestRecyclerAdapter.IRequestsCallback {

    private static final String TAG = RequestsFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fragment_requests_recycler_view)
    RecyclerView mCartsListView;

    private RequestsPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        mPresenter = new RequestsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getAllRequests();
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getAllRequests();
    }

    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        mCartsListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mCartsListView.hasFixedSize();

        mAdapter = new AdminRequestRecyclerAdapter((BaseActivity) getActivity(), this);

        mCartsListView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getAllRequests());
    }

    @Override
    public void updateInfo(List data) {
        mSwipeRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }

    @Override
    public void showError(ErrorType error) {
        Snackbar.make(getActivity().findViewById(R.id.fragment_requests),
                R.string.request_item_error_get_list, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onRequestDeleted() {
        mPresenter.getAllRequests();
    }
}
