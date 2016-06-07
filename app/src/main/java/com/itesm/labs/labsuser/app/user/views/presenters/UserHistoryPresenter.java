package com.itesm.labs.labsuser.app.user.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.user.views.fragments.UserHistoryFragment;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.clients.HistoryClient;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;
import com.mgb.labsapi.models.History;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 4/26/16.
 */
public class UserHistoryPresenter extends BaseFragmentPresenter {

    private static final String TAG = UserHistoryPresenter.class.getSimpleName();

    @Inject
    HistoryClient mHistoryClient;
    @Inject
    ComponentClient mComponentClient;
    @Inject
    CategoryClient mCategoryClient;

    UserHistoryFragment mView;

    ArrayList<History> mUserHistories;

    public UserHistoryPresenter(UserHistoryFragment mView) {
        this.mView = mView;
    }

    public void getUserHistory() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mHistoryClient.getHistoryOf(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), mLabsPreferences.getUser().getUserId()),
                mComponentClient.getAllComponents(mLabsPreferences.getToken(), mLabsPreferences.getLabLink()),
                mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getLabLink()),
                (histories, components, categories) -> {
                    for (History history : histories) {
                        for (Component component : components) {
                            if (component.getId() == history.getComponentId()) {
                                history.setComponentName(component.getName());
                                history.setComponentNote(component.getNote());

                                for (Category category : categories) {
                                    if (category.getId() == component.getCategory()) {
                                        history.setCategoryName(category.getName());
                                    }
                                }
                            }
                        }
                    }

                    return histories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<History>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Refresh task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Refresh task completed");

                        mView.updateInfo(mUserHistories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error on refresh task:" + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<History> histories) {
                        mUserHistories = histories;
                    }
                });
    }
}