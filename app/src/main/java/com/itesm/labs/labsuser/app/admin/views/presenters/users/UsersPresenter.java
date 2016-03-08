package com.itesm.labs.labsuser.app.admin.views.presenters.users;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/20/16.
 */
public class UsersPresenter extends BaseFragmentPresenter {

    private static final String TAG = UsersPresenter.class.getSimpleName();

    @Inject
    UserClient mUserClient;

    private UsersFragment mView;

    private ArrayList<User> mUsersList = new ArrayList<>();

    public UsersPresenter(UsersFragment view) {
        super();
        this.mView = view;
    }

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
                        mView.updateInfo(mUsersList);
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
}
