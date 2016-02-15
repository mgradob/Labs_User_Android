package com.itesm.labs.labsuser.app.admin.views.fragments.inventory;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.mgb.labsapi.clients.CategoryClient;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AllInventoryFragment extends LabsBaseFragment {

    private final String TAG = AllInventoryFragment.class.getSimpleName();

    @Bind(R.id.fragment_inventory_all_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_inventory_all_recycler_view)
    RecyclerView mListView;

    @Inject
    CategoryClient mCategoryClient;

    private AdminCategoryRecyclerAdapter mAdapter;
    private ArrayList<ItemCategory> mCategoriesData = new ArrayList<>();

    public AllInventoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_inventory_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCategoriesList();

        setupRefreshLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        getCategoriesInfo();
    }

    private void setupCategoriesList() {
        mListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mListView.setHasFixedSize(true);

        mAdapter = new AdminCategoryRecyclerAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );
        mRefreshLayout.setOnRefreshListener(() -> getCategoriesInfo());
    }

    @OnItemClick(R.id.fragment_inventory_categories_list)
    void onItemClick(int position) {
        // TODO: 12/27/15 replace to category detail.
    }

    @OnItemLongClick(R.id.fragment_inventory_categories_list)
    void onItemLongClick(int position) {
        // TODO: 12/27/15 replace to category edit.
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getCategoriesInfo() {
        mSubscription.unsubscribe();
        mSubscription = mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink())
                .flatMap(categories -> Observable.from(categories))
                .map(category -> {
                    mCategoriesData.add(new ItemCategory.Builder()
                                    .setId(category.getId())
                                    .setName(category.getName())
                                    .setImageResource(getImageResource(category.getId()))
                                    .build()
                    );
                    return mCategoriesData;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemCategory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get categories started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get categories completed");

                        mAdapter.refresh(mCategoriesData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get categories error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ItemCategory> categories) {
                        mCategoriesData = categories;
                    }
                });
    }

    private int getImageResource(int categoryId) {
        switch (categoryId) {
            case 1:
                return R.drawable.resistencias;
            case 2:
                return R.drawable.capacitores;
            case 3:
                return R.drawable.equipo;
            case 4:
                return R.drawable.kits;
            case 5:
                return R.drawable.cables;
            case 6:
                return R.drawable.integrados;
            case 7:
                return R.drawable.diodos;
            case 8:
                return R.drawable.herramientas;
            case 9:
                return R.drawable.switches;
            case 10:
                return R.drawable.adaptadores;
            case 11:
                return R.drawable.displays;
            case 12:
                return R.drawable.inductores;
            case 13:
                return R.drawable.sensores;
            case 14:
                return R.drawable.motores;
            case 15:
                return R.drawable.potenciometro;
            case 16:
                return R.drawable.transformadores;
            case 17:
                return R.drawable.transistores;
            default:
                return R.drawable.ic_career;

        }
    }
}
