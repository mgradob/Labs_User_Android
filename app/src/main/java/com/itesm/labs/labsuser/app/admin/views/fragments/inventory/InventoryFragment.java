package com.itesm.labs.labsuser.app.admin.views.fragments.inventory;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.presenters.inventory.InventoryPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.itesm.labs.labsuser.app.commons.utils.IFragmentCallback;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class InventoryFragment extends BaseFragment {

    private final String TAG = InventoryFragment.class.getSimpleName();

    @Bind(R.id.fragment_inventory_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_inventory_recycler_view)
    RecyclerView mListView;

    private InventoryPresenter mPresenter;
    private IFragmentCallback<ItemCategory> mCallback;

    private BaseRecyclerAdapter mAdapter;

    // Required empty public constructor
    public InventoryFragment() {
    }

    public static InventoryFragment newInstance(IFragmentCallback callback) {
        InventoryFragment fragment = new InventoryFragment();

        fragment.mCallback = callback;

        return fragment;
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new InventoryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getCategoriesInfo();
    }
    //endregion

    //region UI setup
    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setHasFixedSize(true);

        mAdapter = new AdminCategoryRecyclerAdapter();

        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getCategoriesInfo());
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
    public void onCategoryItemClickEvent(ItemClickEvent<ItemCategory> event) {
        if (event == null) return;

        if (event.getItem() instanceof ItemCategory) {
            mCallback.onListItemClicked(event.getItem());
        }
    }

    @Subscribe
    public void onCategoryItemLongClickEvent(ItemLongClickEvent<ItemCategory> event) {
        if (event == null) return;

        if (event.getItem() instanceof ItemCategory) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }
}
