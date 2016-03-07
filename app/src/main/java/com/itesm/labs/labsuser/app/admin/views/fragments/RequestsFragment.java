package com.itesm.labs.labsuser.app.admin.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.itesm.labs.labsuser.app.commons.utils.FragmentState;
import com.mgb.labsapi.models.CartItem;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by mgradob on 2/12/16.
 */
public class RequestsFragment extends BaseFragment {

    private static final String TAG = RequestsFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fragment_requests_recycler_view)
    RecyclerView mCartsListView;
    @Bind(R.id.fragment_requests_fab)
    FloatingActionButton mFab;

    private BaseRecyclerAdapter mAdapter;

    private RequestsFragmentPresenter mPresenter;

    private long uidFromUser;

    public RequestsFragment() {
        // Required empty public constructor
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        mPresenter = new RequestsFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getAllRequests();
    }
    //endregion

    @OnClick(R.id.fragment_requests_fab)
    void onValidateWithUid() {
        mPresenter.updateUserCart(uidFromUser, false);
    }

    @OnLongClick(R.id.fragment_requests_fab)
    boolean onForceValidate() {
        mPresenter.updateUserCart(uidFromUser, true);
        return true;
    }

    //region UI setup
    @Override
    public void setupUi() {
        setupRequestsList();
        setupRefreshLayout();
        setupFab();
    }

    /**
     * Configures the {@link LinearLayoutManager} and {@link AdminRequestRecyclerAdapter}.
     */
    private void setupRequestsList() {
        mCartsListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mCartsListView.hasFixedSize();

        if (mFragmentState == FragmentState.ITEMS_ALL)
            mAdapter = new AdminRequestRecyclerAdapter();
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mAdapter = new AdminRequestDetailRecyclerAdapter();

        mCartsListView.setAdapter(mAdapter);
    }

    /**
     * Configures the {@link SwipeRefreshLayout}.
     */
    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mFragmentState == FragmentState.ITEMS_ALL)
                mPresenter.getAllRequests();
            else if (mFragmentState == FragmentState.ITEMS_DETAILS)
                mPresenter.getRequestDetail();
        });
    }

    /**
     * Configures the FAB.
     */
    private void setupFab() {
        if (mFragmentState == FragmentState.ITEMS_ALL)
            mFab.setVisibility(View.INVISIBLE);
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mFab.setVisibility(View.VISIBLE);
    }
    //endregion

    //region UI update
    @Override
    public void updateAll(List list) {
        mSwipeRefreshLayout.setRefreshing(false);

        mAdapter.refresh(list);
    }

    @Override
    public void updateDetails(List list) {
        mSwipeRefreshLayout.setRefreshing(false);

        mAdapter.refresh(list);

        if(((CartItem) list.get(0)).isReady())
            mFab.setImageResource(R.drawable.ic_done_white);
        else
            mFab.setImageResource(R.drawable.ic_cart_white);
    }

    @Override
    public void showError() {
        Snackbar.make(getActivity().findViewById(R.id.fragment_requests),
                R.string.request_item_error_get_list, Snackbar.LENGTH_LONG)
                .show();
    }

    public void showValidationSuccess() {
        Snackbar.make(getActivity().findViewById(R.id.fragment_requests),
                R.string.request_item_validate_success, Snackbar.LENGTH_LONG)
                .show();
    }

    public void showValidationError() {
        Snackbar.make(getActivity().findViewById(R.id.fragment_requests),
                R.string.request_item_validate_error, Snackbar.LENGTH_LONG)
                .show();
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
    public void onUIDEvent(UIDEvent event) {
        if (event != null) {
            uidFromUser = event.getUID();
        }
    }

    @Subscribe
    public void onItemClickEvent(ItemClickEvent<ItemUserCart> event) {
        if(event == null) return;

        if(event.getItem() instanceof ItemUserCart) {
            mPresenter.setSelectedUserId(event.getItem().getUserId());
            mFragmentState = FragmentState.ITEMS_DETAILS;

            setupUi();

            mPresenter.getRequestDetail();
        }
    }
    //endregion
}
