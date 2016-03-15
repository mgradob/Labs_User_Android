package com.itesm.labs.labsuser.app.admin.views.fragments.users;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.presenters.users.UsersPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.itesm.labs.labsuser.app.commons.utils.IFragmentCallback;
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

    private UsersPresenter mPresenter;
    private IFragmentCallback<User> mCallback;

    // Required empty public constructor
    public UsersFragment() {
    }

    public static UsersFragment newInstance(IFragmentCallback callback) {
        UsersFragment fragment = new UsersFragment();

        fragment.mCallback = callback;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new UsersPresenter(this);
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

    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setHasFixedSize(true);

        mAdapter = new AdminUserRecyclerAdapter();

        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getUsers());

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
    public void onItemClickEvent(ItemClickEvent<User> event) {
        if (event == null) return;

        if (event.getItem() instanceof User) {
            mCallback.onListItemClicked(event.getItem());
        }
    }

    @Subscribe
    public void onItemLongClickEvent(ItemLongClickEvent<User> event) {
        if (event == null) return;

        if (event.getItem() instanceof User) {
            // TODO: 2/23/16 add edit fragment dialog.
        }
    }
}
