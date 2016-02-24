package com.itesm.labs.labsuser.app.admin.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.adapters.models.ItemHistory;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.clients.HistoryClient;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;
import com.mgb.labsapi.models.History;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/20/16.
 */
public class UsersFragmentPresenter extends BaseFragmentPresenter {

    private static final String TAG = UsersFragmentPresenter.class.getSimpleName();

    @Inject
    HistoryClient mHistoryClient;
    @Inject
    CategoryClient mCategoryClient;
    @Inject
    ComponentClient mComponentClient;
    @Inject
    UserClient mUserClient;

    private UsersFragment mView;

    private ArrayList<User> mUsersList = new ArrayList<>();
    private ArrayList<ItemHistory> mHistoriesList = new ArrayList<>();

    private String selectedUserId;

    public UsersFragmentPresenter(UsersFragment view) {
        super();
        this.mView = view;
    }

    //region UI
    public void setSelectedUserId(User selectedUser) {
        this.selectedUserId = selectedUser.getUserId();
    }
    //endregion

    //region API calls.
    public void getUsers() {
        mSubscription.unsubscribe();
        mSubscription = mUserClient.getUsers(mLabsPreferences.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<User>>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "Task get users started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Task get users completed");
                        mView.updateAll(mUsersList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get users error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(ArrayList<User> users) {
                        mUsersList = users;
                    }
                });
    }

    public void getUserHistory() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mHistoryClient.getHistoryOf(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), selectedUserId),
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
                                    if (category.getId() == component.getId()) {
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
                        mView.updateDetails(mHistoriesList);
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
    //endregion
}
