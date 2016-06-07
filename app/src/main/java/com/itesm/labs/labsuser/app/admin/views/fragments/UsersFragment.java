package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.dialogs.AddUserDialogFragment;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IDialogContract;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends BaseFragment implements IListContract {

    private static final String TAG = UsersFragment.class.getSimpleName();

    @Bind(R.id.fragment_users_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_users_list)
    RecyclerView mListView;

    private UsersPresenter mPresenter;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mPresenter = new UsersPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);
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
    public void onResume() {
        super.onResume();

        mPresenter.getUsers();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_admin_users, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_admin_add_user:
                AddUserDialogFragment fragment = new AddUserDialogFragment();
                fragment.setCallback(new IDialogContract() {
                    @Override
                    public void onActionSuccess() {
                        fragment.dismiss();
                    }

                    @Override
                    public void onActionFailed() {
                        Snackbar.make(view, R.string.event_error_network, Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
                fragment.show(getFragmentManager(), null);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        mAdapter = new AdminUserRecyclerAdapter((BaseActivity) getActivity());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
