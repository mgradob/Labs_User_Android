package com.itesm.labs.labsuser.app.admin.views.fragments.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.users.UsersDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.mgb.labsapi.models.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 3/7/16.
 */
public class UsersDetailFragment extends BaseFragment {

    public static final String TAG = UsersDetailFragment.class.getSimpleName();

    @Bind(R.id.fragment_users_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_users_detail_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private UsersDetailPresenter mPresenter;
    private User mUser;

    // Required empty constructor.
    public UsersDetailFragment() {
    }

    public static UsersDetailFragment newInstance(User user) {
        UsersDetailFragment fragment = new UsersDetailFragment();

        fragment.mUser = user;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        mPresenter = new UsersDetailPresenter(this, mUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getUserDetail();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mAdapter = new AdminUserDetailRecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getUserDetail());
    }

    @Override
    public void updateInfo(List data) {
        mRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }

    @Override
    public void showError(ErrorType error) {

    }
}
