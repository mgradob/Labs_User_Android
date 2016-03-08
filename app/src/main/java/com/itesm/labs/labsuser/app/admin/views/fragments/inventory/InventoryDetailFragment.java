package com.itesm.labs.labsuser.app.admin.views.fragments.inventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.presenters.inventory.InventoryDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.mgb.labsapi.models.Component;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 3/7/16.
 */
public class InventoryDetailFragment extends BaseFragment {

    private static final String TAG = InventoryDetailFragment.class.getSimpleName();

    @Bind(R.id.fragment_inventory_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_inventory_detail_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;

    private InventoryDetailPresenter mPresenter;
    private ItemCategory mCategory;

    // Required empty constructor.
    public InventoryDetailFragment() {
    }

    public static InventoryDetailFragment newInstance(ItemCategory category) {
        InventoryDetailFragment fragment = new InventoryDetailFragment();
        fragment.mCategory = category;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        mPresenter = new InventoryDetailPresenter(this, mCategory);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getCategoryDetail();
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

        mAdapter = new AdminComponentRecyclerAdapter();

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

    @Override
    public void updateInfo(List data) {
        mRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);
    }

    @Override
    public void showError(ErrorType error) {

    }

    @Subscribe
    public void onComponentItemLongClickEvent(ItemLongClickEvent<Component> event) {
        if (event == null) return;

        if (event.getItem() instanceof Component) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }
}
