package com.itesm.labs.labsuser.app.admin.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.adapters.models.ItemHistory;
import com.itesm.labs.labsuser.app.admin.views.activities.UserDetailActivity;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
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
 * Created by mgradob on 3/7/16.
 */
public class UsersDetailPresenter extends BaseFragmentPresenter {

    private static final String TAG = UsersDetailPresenter.class.getSimpleName();

    @Inject
    HistoryClient mHistoryClient;
    @Inject
    CategoryClient mCategoryClient;
    @Inject
    ComponentClient mComponentClient;

    private UserDetailActivity mView;
    private ArrayList<ItemHistory> mHistoriesList = new ArrayList<>();

    private String mUserId;

    public UsersDetailPresenter(UserDetailActivity mView, String userId) {
        super();
        this.mView = mView;
        this.mUserId = userId;
    }

    public void getUserDetail() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mHistoryClient.getHistoryOf(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), mUserId),
                mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink()),
                mComponentClient.getAllComponents(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink()),
                (histories, categories, components) -> {
                    ArrayList<ItemHistory> itemHistories = new ArrayList<>();

                    for (History history : histories) {
                        ItemHistory.Builder builder = new ItemHistory.Builder();

                        builder.setHistory(history);

                        for (Component component : components) {
                            if (component.getId() == history.getComponentId()) {
                                builder.setComponentName(component.getName());
                                builder.setComponentNote(component.getNote());

                                for (Category category : categories) {
                                    if (category.getId() == component.getCategory()) {
                                        builder.setCategoryName(category.getName());
                                    }
                                }
                            }
                        }

                        itemHistories.add(builder.build());
                    }

                    return itemHistories;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemHistory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get histories started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get histories completed");
                        mView.updateInfo(mHistoriesList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get histories error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ArrayList<ItemHistory> itemHistories) {
                        mHistoriesList = itemHistories;
                    }
                });
    }
}
