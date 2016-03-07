package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.utils.FragmentState;
import com.mgb.labsapi.models.User;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends BaseFragment {

    private static final String TAG = UsersFragment.class.getSimpleName();

    @Bind(R.id.fragment_users_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_users_list)
    RecyclerView mListView;

    private UsersFragmentPresenter mPresenter;

    private BaseRecyclerAdapter mAdapter;

    public UsersFragment() {
        // Required empty public constructor
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new UsersFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getUsers();
    }
    //endregion

    //region UI setup
    @Override
    public void setupUi() {
        setupUsersList();
        setupRefreshLayout();
    }

    private void setupUsersList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setHasFixedSize(true);

        if (mFragmentState == FragmentState.ITEMS_ALL)
            mAdapter = new AdminUserRecyclerAdapter();
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mAdapter = new AdminUserDetailRecyclerAdapter();

        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        if (mFragmentState == FragmentState.ITEMS_ALL)
            mRefreshLayout.setOnRefreshListener(() -> mPresenter.getUsers());
        else if (mFragmentState == FragmentState.ITEMS_DETAILS)
            mRefreshLayout.setOnRefreshListener(() -> mPresenter.getUserHistory());
    }
    //endregion

    //region UI
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
    public void onItemClickEvent(ItemClickEvent<User> event) {
        if (event == null) return;

        if(event.getItem() instanceof User) {
            mPresenter.setSelectedUserId(event.getItem());
            mFragmentState = FragmentState.ITEMS_DETAILS;

            setupUi();

            mPresenter.getUserHistory();
        }
    }

    @Subscribe
    public void onItemLongClickEvent(ItemLongClickEvent<User> event) {
        if (event == null) return;

        if(event.getItem() instanceof User) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }
    //endregion
}
