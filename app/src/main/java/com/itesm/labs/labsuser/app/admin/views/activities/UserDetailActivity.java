package com.itesm.labs.labsuser.app.admin.views.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailActivity extends BaseActivity implements IListContract {

    public static final String TAG = UserDetailActivity.class.getSimpleName();
    public static final String EXTRA_USER_ID = "USER_ID";

    @Bind(R.id.activity_users_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.activity_users_detail_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private BaseRecyclerAdapter mAdapter;
    private UsersDetailPresenter mPresenter;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mUserId = extras.getString(EXTRA_USER_ID);

        mPresenter = new UsersDetailPresenter(this, mUserId);

        setupUi();

        mPresenter.getUserDetail();
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

        mAdapter = new AdminUserDetailRecyclerAdapter(this);

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

    public void showError(ErrorType error) {

    }
}
