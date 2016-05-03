package com.itesm.labs.labsuser.app.user.views.presenters;

import android.content.Context;
import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.user.views.fragments.UserInventoryFragment;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.models.Category;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 4/25/16.
 */
public class UserInventoryPresenter extends BaseFragmentPresenter {

    private static final String TAG = UserInventoryPresenter.class.getSimpleName();

    @Inject
    CategoryClient mCategoryClient;

    UserInventoryFragment mView;

    private ArrayList<ItemCategory> mCategoriesData = new ArrayList<>();

    public UserInventoryPresenter(UserInventoryFragment mView) {
        super();
        this.mView = mView;
    }

    public void getCategories() {
        mCategoriesData.clear();

        mSubscription.unsubscribe();
        mSubscription = mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getLabLink())
                .flatMap(categories -> Observable.from(categories))
                .map(category -> {
                    if (category.getId() == 1 || category.getId() == 2 || category.getId() == 6 || category.getId() == 7 || category.getId() == 9 || category.getId() == 11 || category.getId() == 15 || category.getId() == 17 || category.getId() == 18) {
                            return mCategoriesData;
                    }
                    mCategoriesData.add(new ItemCategory.Builder()
                            .setId(category.getId())
                            .setName(category.getName())
                            .setImageResource(getImageResource(category.getId()))
                            .build());
                    return mCategoriesData;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemCategory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task load categories started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task load categories completed");

                        mView.updateInfo(mCategoriesData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get components error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ItemCategory> itemCategories) {
                        mCategoriesData = itemCategories;
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
