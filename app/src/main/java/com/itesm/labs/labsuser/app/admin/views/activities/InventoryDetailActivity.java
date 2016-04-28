package com.itesm.labs.labsuser.app.admin.views.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.InventoryDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InventoryDetailActivity extends BaseActivity implements IListContract {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

    private static final String TAG = InventoryDetailActivity.class.getSimpleName();

    @Bind(R.id.activity_inventory_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.activity_inventory_detail_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;

    private BaseRecyclerAdapter mAdapter;
    private InventoryDetailPresenter mPresenter;
    private int mCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mCategoryId = extras.getInt(EXTRA_CATEGORY_ID);

        mPresenter = new InventoryDetailPresenter(this, mCategoryId);

        setupUi();

        mPresenter.getCategoryDetail();
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

        mAdapter = new AdminComponentRecyclerAdapter(this);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getCategoryDetail());
    }

    public void showError(ErrorType error) {

    }

    @Override
    public void updateInfo(List data) {
        mRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }
}
