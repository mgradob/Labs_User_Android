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
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 2/15/16.
 */
public class InventoryDetailFragment extends LabsBaseFragment {

    private static final String TAG = InventoryDetailFragment.class.getSimpleName();
    private static final String KEY_CATEGORY_ID = "KEY_CATEGORY_ID";

    @Bind(R.id.fragment_inventory_detail_swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fragment_inventory_detail_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    ComponentClient mComponentClient;
    
    private int selectedCategoryId;
    private AdminComponentRecyclerAdapter mAdapter;
    private ArrayList<Component> components = new ArrayList<>();

    public InventoryDetailFragment() {
    }

    public static InventoryDetailFragment newInstance(int categoryId){
        InventoryDetailFragment fragment = new InventoryDetailFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedCategoryId = getArguments().getInt(KEY_CATEGORY_ID);

        setupSwipeLayout();

        setupRecyclerView();
    }

    private void setupSwipeLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mSwipeRefreshLayout.setOnRefreshListener(() -> getComponents(selectedCategoryId));
    }

    private void setupRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mAdapter = new AdminComponentRecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }

    private void getComponents(int categoryId){
        // TODO: 2/17/16  
    }
}
