package com.itesm.labs.labsuser.app.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.adapters.RecordRecyclerAdapter;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.application.AppGlobals;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.rest.clients.RecordClient;
import com.itesm.labs.labsuser.app.rest.models.History;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecordFragment extends LabsBaseFragment {

    private static final String TAG = RecordFragment.class.getSimpleName();

    @Bind(R.id.record_list_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.record_list_view_swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Inject
    AppGlobals mAppGlobals;

    @Inject
    RecordClient mRecordClient;

    private RecordRecyclerAdapter mAdapter;

    private ArrayList<History> mHistoryArrayList = new ArrayList<>();

    public RecordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRefreshLayout();

        setupRecordList();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isAdded())
            refreshList();
    }

    private void setupRecordList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.hasFixedSize();

        mAdapter = new RecordRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
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

    public void refreshList() {
        mRecordClient.getRecordOf(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(),
                mAppGlobals.getUser().getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<History>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Refresh task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Refresh task completed");
                        refreshLayout.setRefreshing(false);

                        mAdapter.refresh(mHistoryArrayList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error on refresh task");

                        if (mSnackbarUtil != null)
                            mSnackbarUtil.showSnackbar(getString(R.string.record_snackbar_error_content));
                    }

                    @Override
                    public void onNext(ArrayList<History> histories) {
                        if (histories == null)
                            throw new NullPointerException("hisotries is null");

                        mHistoryArrayList = histories;

                        mAppGlobals.setHistory(mHistoryArrayList);
                    }
                });
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
}