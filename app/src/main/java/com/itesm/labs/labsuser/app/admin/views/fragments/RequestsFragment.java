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
import com.itesm.labs.labsuser.app.admin.adapters.RequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.UserClient;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends LabsBaseFragment {

    private final String TAG = RequestsFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_list)
    RecyclerView mListView;
    @Bind(R.id.fragment_requests_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    CartClient mCartClient;
    @Inject
    UserClient mUserClient;

    private RequestRecyclerAdapter mAdapter;
    private ArrayList<ItemUserCart> cartsList = new ArrayList<>();

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRequestsList();

        setupRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnItemClick(R.id.fragment_requests_list)
    void onCartClicked(int position) {
        // TODO: 12/27/15 replace
//        mAppGlobals.setCart(cartsList.get(position));
//
//        Intent intent = new Intent(mContext, RequestDetailActivity.class);
//        intent.putExtra("USERNAME", cartsList.get(position).getUserName());
//        intent.putExtra("USERID", cartsList.get(position).getUserId());
//        intent.putExtra("REQUESTSTATUS", cartsList.get(position).isReady());
//        startActivityForResult(intent, REQUEST_DETAIL_REQUEST);
    }

    private void setupRequestsList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.hasFixedSize();

        mAdapter = new RequestRecyclerAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> getRequests());
    }
}
