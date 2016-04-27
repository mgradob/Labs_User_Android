package com.itesm.labs.labsuser.app.user.views.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.itesm.labs.labsuser.app.user.adapters.UserCartRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserCartPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCartFragment extends BaseFragment implements IListContract {

    private static final String TAG = UserCartFragment.class.getSimpleName();

    @Bind(R.id.fragment_cart_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_cart_fab)
    FloatingActionButton fab;
    @Bind(R.id.fragment_cart_list_view_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private UserCartPresenter mPresenter;

    private UserCartRecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter = new UserCartPresenter(this);
        mPresenter.getCart();
    }

    @Override
    protected void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    protected void showError(ErrorType error) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setupList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mAdapter = new UserCartRecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        refreshLayout.setColorSchemeResources(
                R.color.material_red,
                R.color.material_pink,
                R.color.material_deep_purple,
                R.color.material_indigo,
                R.color.material_blue,
                R.color.material_light_blue,
                R.color.material_cyan,
                R.color.material_teal,
                R.color.material_green,
                R.color.material_light_green,
                R.color.material_yellow,
                R.color.material_amber,
                R.color.material_deep_orange,
                R.color.material_brown,
                R.color.material_grey
        );
        refreshLayout.setOnRefreshListener(() -> mPresenter.getCart());
    }

    @Override
    public void updateInfo(List data) {
        mAdapter.refresh(data);
    }

    @OnClick(R.id.fragment_cart_fab)
    void postCart() {
        // Actualizar todos los items que haya actualmente en la base de datos, ya tenemos una
        // lista con todos los items nuevos en userCart.

        mPresenter.postUserCart();
    }
}
