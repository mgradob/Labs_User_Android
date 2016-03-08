package com.itesm.labs.labsuser.app.admin.views.fragments.inventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.bases.BaseControllerFragment;
import com.itesm.labs.labsuser.app.commons.utils.IFragmentCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 3/7/16.
 */
public class InventoryControllerFragment extends BaseControllerFragment implements IFragmentCallback<ItemCategory> {

    private static final String TAG = InventoryControllerFragment.class.getSimpleName();

    private static final String BACKSTACK_INVENTORY_DETAIL = "INVENTORY_DETAIL";

    @Bind(R.id.inventory_controller_container)
    FrameLayout mContainer;

    InventoryFragment mInventoryFragment;
    InventoryDetailFragment mInventoryDetailFragment;

    // Required empty constructor
    public InventoryControllerFragment() {
    }

    public static InventoryControllerFragment newInstance() {
        return new InventoryControllerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_controller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentManager = getFragmentManager();

        mInventoryFragment = InventoryFragment.newInstance(this);
        mFragmentManager.beginTransaction()
                .replace(R.id.inventory_controller_container, mInventoryFragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onListItemClicked(ItemCategory data) {
        mInventoryDetailFragment = InventoryDetailFragment.newInstance(data);

        mFragmentManager.beginTransaction()
                .replace(R.id.inventory_controller_container, mInventoryDetailFragment)
                .addToBackStack(BACKSTACK_INVENTORY_DETAIL)
                .commit();
    }
}
