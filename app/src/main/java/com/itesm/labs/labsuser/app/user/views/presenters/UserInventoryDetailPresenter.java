package com.itesm.labs.labsuser.app.user.views.presenters;

import android.content.Context;
import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.user.views.activities.UserInventoryDetailActivity;
import com.itesm.labs.labsuser.app.user.views.fragments.UserInventoryFragment;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 4/25/16.
 */
public class UserInventoryDetailPresenter extends BaseFragmentPresenter {

    private static final String TAG = UserInventoryDetailPresenter.class.getSimpleName();

    @Inject
    Context mContext;
    @Inject
    ComponentClient mComponentClient;

    UserInventoryDetailActivity mView;

    private ArrayList<Component> mComponentsData = new ArrayList<>();
    private int mCategoryId;

    public UserInventoryDetailPresenter(UserInventoryDetailActivity mView, int mCategoryId) {
        super();
        this.mView = mView;
        this.mCategoryId = mCategoryId;
    }

    public void getComponents() {
        mSubscription.unsubscribe();
        mSubscription = mComponentClient.getComponentsOfCategory(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mCategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Component>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get components started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get components completed");

                        mView.updateInfo(mComponentsData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get components error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Component> components) {
                        mComponentsData = components;
                    }
                });
    }
}
