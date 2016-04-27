package com.itesm.labs.labsuser.app.commons.views.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.views.presenters.LabsActivityPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LabsActivity extends BaseActivity {

    private static final String TAG = LabsActivity.class.getSimpleName();

    @Bind(R.id.labs_grid)
    RecyclerView mRecyclerView;
    @Bind(R.id.labs_grid_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private LabsRecyclerAdapter mLabsAdapter;

    private LabsActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);

        ButterKnife.bind(this);

        mPresenter = new LabsActivityPresenter(this);

        setupUi();
    }

    @Override
    public void setupUi() {
        setupStatusBar(getResources().getColor(R.color.primary));

        setupRefreshLayout();

        setupLabsGrid();

        mPresenter.getAllowedLabs();
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
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
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getAllowedLabs());
    }

    private void setupLabsGrid() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setHasFixedSize(true);

        mLabsAdapter = new LabsRecyclerAdapter();
        mRecyclerView.setAdapter(mLabsAdapter);
    }

    public void updateInfo(List list) {
        mLabsAdapter.refresh(list);
        mRefreshLayout.setRefreshing(false);
    }

    public void showError() {
        mRefreshLayout.setRefreshing(false);
    }
}
