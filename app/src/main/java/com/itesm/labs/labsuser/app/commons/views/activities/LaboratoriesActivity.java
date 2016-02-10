package com.itesm.labs.labsuser.app.commons.views.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.models.ItemLaboratory;
import com.mgb.labsapi.clients.LaboratoryClient;
import com.mgb.labsapi.models.Laboratory;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LaboratoriesActivity extends LabsBaseActivity {

    private static final String TAG = LaboratoriesActivity.class.getSimpleName();

    @Bind(R.id.labs_grid)
    RecyclerView mRecyclerView;

    @Bind(R.id.labs_grid_swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Inject
    LaboratoryClient mLaboratoryClient;

    private ArrayList<ItemLaboratory> labsList = new ArrayList<>();

    private LabsRecyclerAdapter mLabsAdapter;

    private MaterialDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);

        ButterKnife.bind(this);

        setupRefreshLayout();

        setupLabsGrid();

        getAllowedLabs();
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllowedLabs();
            }
        });
    }

    private void setupLabsGrid() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));

        mRecyclerView.setHasFixedSize(true);

        mLabsAdapter = new LabsRecyclerAdapter();
        mLabsAdapter.setOnClickListener(new BaseRecyclerAdapter.IOnClickListener() {
            @Override
            public void onItemClick(int position) {
                String[] params = labsList.get(position).getLaboratory().getLink().split("/");
                mLabsPreferences.putLabLink(params[params.length - 1]);
                mLabsPreferences.putCurrentLab(labsList.get(position).getLaboratory());
                mLabsPreferences.putLabColor(labsList.get(position).getColorResource());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_in_top);
            }
        });
        mRecyclerView.setAdapter(mLabsAdapter);
    }

    private void showDialog(boolean show) {
        if (show) {
            mDialog = new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.labs_dialog_title))
                    .content(getResources().getString(R.string.labs_dialog_content))
                    .progress(true, 100)
                    .show();
        } else {
            mDialog.dismiss();
        }
    }

    private void getAllowedLabs() {
        mLaboratoryClient.getLaboratories(mLabsPreferences.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Laboratory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get allowed labs started");
                        showDialog(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get allowed labs completed");
                        showDialog(false);
                        mRefreshLayout.setRefreshing(false);

                        mLabsAdapter.refresh(labsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get allowed labs error: " + e.getMessage());

                        showDialog(false);
                        mRefreshLayout.setRefreshing(false);

                        Snackbar.make(findViewById(R.id.labs_activity),
                                getResources().getString(R.string.labs_snackbar_error_content),
                                Snackbar.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onNext(ArrayList<Laboratory> laboratories) {
                        if (laboratories == null)
                            throw new NullPointerException("Error getting labs, " + laboratories);

                        int[] colors = getResources().getIntArray(R.array.material_colors);
                        Random random = new Random(colors.length-1);

                        for (Laboratory laboratory : laboratories)
                            labsList.add(new ItemLaboratory.Builder()
                                            .setLaboratory(laboratory)
                                            .setColorResource(colors[random.nextInt()])
                                            .build()
                            );
                    }
                });
    }
}
