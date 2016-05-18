package com.itesm.labs.labsuser.app.user.views.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

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
    public static final String EXTRA_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_IMAGE = "CATEGORY_IMAGE";

    @Bind(R.id.activity_inventory_detail_recycler_view)
    RecyclerView activityInventoryDetailRecyclerView;
    @Bind(R.id.activity_inventory_detail_swipe_layout)
    SwipeRefreshLayout activityInventoryDetailSwipeLayout;
    @Bind(R.id.activity_inventory_detail_image)
    ImageView activityInventoryDetailImage;
    @Bind(R.id.activity_inventory_detail_toolbar)
    Toolbar activityInventoryDetailToolbar;
    @Bind(R.id.activity_inventory_detail_ctl)
    CollapsingToolbarLayout activityInventoryDetailCtl;

    private UserInventoryDetailPresenter mPresenter;

    private UserComponentRecyclerAdapter mAdapter;

    private int mCategoryId;
    private String mCategoryName;
    private int mCategoryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mCategoryId = extras.getInt(EXTRA_CATEGORY_ID);
        mCategoryName = extras.getString(EXTRA_CATEGORY_NAME);
        mCategoryImage = extras.getInt(EXTRA_CATEGORY_IMAGE);

        setupUi();

        mPresenter = new UserInventoryDetailPresenter(this, mCategoryId);
        mPresenter.getComponents();
    }

    @Override
    public void setupUi() {
        setupToolbar();
        setupList();
        setupRefresh();
    }

    public void setupToolbar() {
        setSupportActionBar(activityInventoryDetailToolbar);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mCategoryImage);

        if (bitmap == null) return;

        Palette palette = Palette.from(bitmap).generate();

        activityInventoryDetailImage.setImageBitmap(bitmap);

        activityInventoryDetailCtl.setBackgroundColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        activityInventoryDetailCtl.setContentScrimColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        activityInventoryDetailCtl.setCollapsedTitleTextColor(getResources().getColor(R.color.primary_text_light));
        activityInventoryDetailCtl.setExpandedTitleColor(getResources().getColor(R.color.primary_text_light));
        activityInventoryDetailCtl.setTitle(mCategoryName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
        }
    }

    @Override
    public void setupList() {
        activityInventoryDetailRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        activityInventoryDetailRecyclerView.setHasFixedSize(true);

        mAdapter = new UserComponentRecyclerAdapter(this);

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
        activityInventoryDetailSwipeLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }
}
