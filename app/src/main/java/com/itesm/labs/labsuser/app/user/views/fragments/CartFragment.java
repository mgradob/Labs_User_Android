package com.itesm.labs.labsuser.app.user.views.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.user.adapters.CartRecyclerAdapter;
import com.itesm.labs.labsuser.app.rest.clients.ApiService;
import com.mgb.labsapi.models.CartItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartFragment extends LabsBaseFragment {

    private static final String TAG = CartFragment.class.getSimpleName();

    @Bind(R.id.fragment_cart_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.fragment_cart_fab)
    FloatingActionButton fab;

    @Bind(R.id.fragment_cart_list_view_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Inject
    ApiService mApiService;

    private CartRecyclerAdapter mAdapter;

    private ArrayList<CartItem> mCartList = new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

        setupRefreshLayout();

        setupListView();
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshList();
    }

    private void setupRefreshLayout() {
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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    private void setupListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.hasFixedSize();

        mAdapter = new CartRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fragment_cart_fab)
    void postCart() {
        // Actualizar todos los items que haya actualmente en la base de datos, ya tenemos una
        // lista con todos los items nuevos en userCart.

        deleteUserCart();
    }

    public void refreshList() {
        mSubscription.unsubscribe();
        mSubscription = mApiService.getCartItemsOf(mLabsPreferences.getToken(),
                mLabsPreferences.getLabLink(),
                mLabsPreferences.getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Starting refresh task");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Refresh task complete");
                        refreshLayout.setRefreshing(false);

                        mAdapter.refresh(mCartList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error on refresh task");
                        refreshLayout.setRefreshing(false);

                        Snackbar.make(getActivity().findViewById(R.id.cart_fragment),
                                R.string.cart_snackbar_error_content,
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> cartItems) {

                    }
                });

//        mCartClient.getCartItemsOf(mLabsPreferences.getToken(),
//                mLabsPreferences.getCurrentLab().getLink(),
//                mLabsPreferences.getUser().getUserId())
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ArrayList<CartItem>>() {
//                    @Override
//                    public void onStart() {
//                        Log.d(TAG, "Starting refresh task");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "Refresh task complete");
//                        refreshLayout.setRefreshing(false);
//
//                        mAdapter.refresh(mAppGlobals.getUserCart().getCartList());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "Error on refresh task");
//                        refreshLayout.setRefreshing(false);
//
//                        Snackbar.make(getView(),
//                                getResources().getString(R.string.cart_snackbar_error_content),
//                                Snackbar.LENGTH_LONG)
//                                .show();
//                    }
//
//                    @Override
//                    public void onNext(ArrayList<CartItem> cartItems) {
//                        if (cartItems == null)
//                            throw new NullPointerException("Cart is null");
//
//                        mAppGlobals.getUserCart().getCartList().clear();
//                        mAppGlobals.getUserCart().getCartList().addAll(cartItems);
//                    }
//                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void deleteUserCart() {
        mApiService.deleteCart(mLabsPreferences.getToken(),
                mLabsPreferences.getCurrentLab().getLink(),
                mLabsPreferences.getUser().getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Delete task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Delete task finished");

                        postNewCart();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error on delete task");

                    }

                    @Override
                    public void onNext(Void aVoid) {
                        for (CartItem item : mCartList) {
                            item.setReady(false);
                        }
                    }
                });
    }

    private void postNewCart() {
        mApiService.postNewCart(mLabsPreferences.getToken(),
                mLabsPreferences.getCurrentLab().getLink(),
                mCartList,
                mLabsPreferences.getUser())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Post task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Post task finished");

                        mCartList.clear();

                        Snackbar.make(getActivity().findViewById(R.id.cart_fragment),
                                getResources().getString(R.string.cart_snackbar_post_success_content),
                                Snackbar.LENGTH_LONG)
                                .show();

                        refreshList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error on post task");

                        Snackbar.make(getActivity().findViewById(R.id.cart_fragment),
                                getResources().getString(R.string.cart_snackbar_post_error_content),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                    }
                });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            mAdapter.refresh(mCartList);
        }
    }
}
