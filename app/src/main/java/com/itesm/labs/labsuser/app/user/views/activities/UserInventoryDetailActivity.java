package com.itesm.labs.labsuser.app.user.views.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.user.adapters.UserComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserInventoryDetailPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInventoryDetailActivity extends BaseActivity implements IListContract {

    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";

    @Bind(R.id.activity_inventory_detail_recycler_view)
    RecyclerView activityInventoryDetailRecyclerView;
    @Bind(R.id.activity_inventory_detail_swipe_layout)
    SwipeRefreshLayout activityInventoryDetailSwipeLayout;

    private UserInventoryDetailPresenter mPresenter;

    private UserComponentRecyclerAdapter mAdapter;

    private int mCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mCategoryId = extras.getInt(EXTRA_CATEGORY_ID);

        mPresenter = new UserInventoryDetailPresenter(this, mCategoryId);
    }

    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        activityInventoryDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        activityInventoryDetailRecyclerView.setHasFixedSize(true);

        mAdapter = new UserComponentRecyclerAdapter();

        activityInventoryDetailRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        activityInventoryDetailSwipeLayout.setColorSchemeResources(
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
        activityInventoryDetailSwipeLayout.setOnRefreshListener(() -> {
            mPresenter.getComponents();
        });
    }

    @Override
    public void updateInfo(List data) {
        mAdapter.refresh(data);
    }
}
