package com.itesm.labs.labsuser.app.admin.views.presenters.inventory;

import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/19/16.
 */
public class InventoryPresenter extends BaseFragmentPresenter {

    private final String TAG = InventoryPresenter.class.getSimpleName();

    @Inject
    CategoryClient mCategoryClient;

    private InventoryFragment mView;

    private ArrayList<ItemCategory> mCategoriesData = new ArrayList<>();

    public InventoryPresenter(InventoryFragment view) {
        super();
        this.mView = view;
    }

    public void getCategoriesInfo() {
        mCategoriesData.clear();

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

                        mView.updateInfo(mCategoriesData);
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
