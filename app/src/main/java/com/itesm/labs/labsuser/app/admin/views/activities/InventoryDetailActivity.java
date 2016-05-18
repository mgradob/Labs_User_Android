package com.itesm.labs.labsuser.app.admin.views.activities;

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
    public static final String EXTRA_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_IMAGE = "CATEGORY_IMAGE";

    private static final String TAG = InventoryDetailActivity.class.getSimpleName();

    @Bind(R.id.activity_inventory_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.activity_inventory_detail_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.activity_inventory_detail_image)
    ImageView activityInventoryDetailImage;
    @Bind(R.id.activity_inventory_detail_toolbar)
    Toolbar activityInventoryDetailToolbar;
    @Bind(R.id.activity_inventory_detail_ctl)
    CollapsingToolbarLayout activityInventoryDetailCtl;

    private BaseRecyclerAdapter mAdapter;
    private InventoryDetailPresenter mPresenter;

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

        mPresenter = new InventoryDetailPresenter(this, mCategoryId);

        setupUi();

        mPresenter.getCategoryDetail();
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
