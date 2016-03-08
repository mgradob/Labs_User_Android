package com.itesm.labs.labsuser.app.admin.views.presenters.inventory;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryDetailFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 3/7/16.
 */
public class InventoryDetailPresenter extends BaseFragmentPresenter {

    private static final String TAG = InventoryDetailPresenter.class.getSimpleName();

    @Inject
    ComponentClient mComponentClient;

    private InventoryDetailFragment mView;
    private ItemCategory mCategory;

    private ArrayList<Component> mComponents = new ArrayList<>();

    public InventoryDetailPresenter(InventoryDetailFragment view, ItemCategory category) {
        super();
        this.mView = view;
        this.mCategory = category;
    }

    public void getCategoryDetail() {
        mSubscription.unsubscribe();
        mSubscription = mComponentClient.getComponentsOfCategory(mLabsPreferences.getToken(),
                mLabsPreferences.getCurrentLab().getLink(), mCategory.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Component>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get components started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get categories completed");

                        mView.updateInfo(mComponents);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get categories error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Component> components) {
                        mComponents = components;
                    }
                });

    }
}
