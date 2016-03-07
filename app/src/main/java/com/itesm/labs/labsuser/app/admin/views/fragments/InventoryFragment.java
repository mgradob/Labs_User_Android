package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.presenters.InventoryFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.utils.FragmentState;
import com.mgb.labsapi.models.Component;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class InventoryFragment extends BaseFragment {

    private final String TAG = InventoryFragment.class.getSimpleName();

    @Bind(R.id.fragment_inventory_all_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_inventory_all_recycler_view)
    RecyclerView mListView;

    private InventoryFragmentPresenter mPresenter;

    private BaseRecyclerAdapter mAdapter;

    public InventoryFragment() {
        // Required empty public constructor
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new InventoryFragmentPresenter(this);
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
        setupCategoriesList();
        setupRefreshLayout();
    }

    private void setupCategoriesList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setHasFixedSize(true);

        if (mFragmentState == FragmentState.ITEMS_ALL)
            mAdapter = new AdminCategoryRecyclerAdapter();
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mAdapter = new AdminComponentRecyclerAdapter();

        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        if (mFragmentState == FragmentState.ITEMS_ALL)
            mRefreshLayout.setOnRefreshListener(() -> mPresenter.getCategoriesInfo());
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mRefreshLayout.setOnRefreshListener(() -> mPresenter.getCategoryDetail());
    }
    //endregion

    //region UI Update
    @Override
    public void updateAll(List list) {
        mAdapter.refresh(list);
    }

    @Override
    public void updateDetails(List list) {
        mAdapter.refresh(list);
    }

    @Override
    public void showError() {

    }
    //endregion

    //region Event bus
    @Subscribe
    public void onBackPressedEvent(BackPressedEvent event) {
        if (mFragmentState == FragmentState.ITEMS_ALL) {
            getActivity().finish();
        } else if (mFragmentState == FragmentState.ITEMS_DETAILS) {
            mFragmentState = FragmentState.ITEMS_ALL;
            setupUi();
        }
    }

    @Subscribe
    public void onCategoryItemClickEvent(ItemClickEvent<ItemCategory> event) {
        if (event == null) return;

        if(event.getItem() instanceof ItemCategory) {
            mPresenter.setSelectedCategory(event.getItem());
            mFragmentState = FragmentState.ITEMS_DETAILS;

            setupUi();

            mPresenter.getCategoryDetail();
        }
    }

    @Subscribe
    public void onCategoryItemLongClickEvent(ItemLongClickEvent<ItemCategory> event) {
        if (event == null) return;

        if(event.getItem() instanceof ItemCategory) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }

    @Subscribe
    public void onComponentItemLongClickEvent(ItemLongClickEvent<Component> event) {
        if (event == null) return;

        if(event.getItem() instanceof Component) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }
    //endregion
}
