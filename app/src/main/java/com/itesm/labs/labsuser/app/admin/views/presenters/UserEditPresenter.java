package com.itesm.labs.labsuser.app.admin.views.presenters;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.util.Log;

import com.itesm.labs.labsuser.app.admin.views.activities.UserEditActivity;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.utils.NfcController;
import com.itesm.labs.labsuser.app.commons.utils.NfcHandler;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.User;

import javax.inject.Inject;

import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 4/21/16.
 */
public class UserEditPresenter extends BaseActivityPresenter {

    private static final String TAG = UserEditPresenter.class.getSimpleName();

    @Inject
    UserClient mUserClient;
    @Inject
    LabsPreferences mPreferences;
    @Inject
    NfcController mNfcController;

    User mUser;

    private UserEditActivity mView;

    public UserEditPresenter(UserEditActivity view) {
        this.mView = view;
    }

    public void setupNfc() {
        mNfcController.setupNfc(mView);
    }

    public void enableNfc() {
        mNfcController.enableNfc(mView);
    }

    public void disableNfc() {
        mNfcController.disableNfc(mView);
    }

    public void getUser(String userId) {
        mSubscription.unsubscribe();
        mSubscription = mUserClient.getUser(mPreferences.getToken(), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "Task get user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Task get user completed");

                        mView.fillUserInfo(mUser);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get user error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(User user) {
                        if(user == null) throw new NullPointerException("User is null");

                        mUser = user;
                    }
                });
    }

    public void doEdit(String userId, String name, String lastName1, String lastName2, String career, long uid) {
        User.Builder builder = new User.Builder()
                .setUserId(userId)
                .setUserName(name)
                .setUserLastName1(lastName1)
                .setUserLastName2(lastName2)
                .setUserCareer(career)
                .setAllowedLabs(mUser.getAllowedLabs())
                .setUserUid(uid);

        mSubscription.unsubscribe();
        mSubscription = mUserClient.editUser(mPreferences.getToken(), mUser.getUserId(), builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "Task update user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "Task update user completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task update user error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}