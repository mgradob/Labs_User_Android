package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.RequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemRequestDetail;
import com.itesm.labs.labsuser.app.rest.clients.ApiService;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestDetailFragment extends LabsBaseFragment {

    @Bind(R.id.fragment_request_detal_user_info)
    TextView mDetalUserInfo;
    @Bind(R.id.fragment_request_detail_list)
    RecyclerView mDetailList;
    @Bind(R.id.fragment_request_detail_fab)
    FloatingActionButton mDetailFab;

    @Inject
    ApiService mApiService;

    ArrayList<ItemRequestDetail> userCart = new ArrayList<>();

    RequestDetailRecyclerAdapter mAdapter;

    public RequestDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void configureDetailList(){
        mDetailList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mDetailList.hasFixedSize();

        mAdapter = new RequestDetailRecyclerAdapter();
        mDetailList.setAdapter(mAdapter);
    }

    private void getUserCart(){
        mSubscription.unsubscribe();
        mSubscription = mApiService.getCartItemsOf(mLabsPreferences.getToken(),
                mLabsPreferences.getCurrentLab().getLink(),
                userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemRequestDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<ItemRequestDetail> cartItems) {

                    }
                });
    }
}
