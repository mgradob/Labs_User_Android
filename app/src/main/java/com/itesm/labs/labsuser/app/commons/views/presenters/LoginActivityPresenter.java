package com.itesm.labs.labsuser.app.commons.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.LoginBody;
import com.mgb.labsapi.models.User;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/21/16.
 */
public class LoginActivityPresenter extends BaseActivityPresenter {

    private static final String TAG = LoginActivityPresenter.class.getSimpleName();

    @Inject
    UserClient mUserClient;

    private LoginActivity mView;

    private String regexUserId = "^[alAL][\\d]{8}$";

    public LoginActivityPresenter(LoginActivity mView) {
        super();
        this.mView = mView;
    }

    //region Api calls
    public void loginUser() {
        String userId = mLabsPreferences.getUserId();
        if (userId.matches(regexUserId)) {
            userId = userId.charAt(0) == 'a' ? userId.replace('a', 'A') :
                    userId.charAt(0) == 'l' ? userId.replace('l', 'L') : userId;
        } else {
            mView.displayLoginError();
            return;
        }
        mLabsPreferences.putUserId(userId);

        mSubscription.unsubscribe();
        mSubscription = mUserClient.loginUser(new LoginBody.Builder()
                .setId_student(mLabsPreferences.getUserId())
                .setPassword(mLabsPreferences.getUserPass()).build())
                .doOnNext(auth -> {
                    mLabsPreferences.putToken(auth.getAuthToken());
                })
                .flatMap(auth1 -> mUserClient.getUser(auth1.getAuthToken(), mLabsPreferences.getUserId())
                        .doOnNext(user -> mLabsPreferences.putUser(user)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task login user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task login user completed");

                        mView.goToLabsView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task login user error: " + e.getMessage());

                        mView.displayLoginError();
                    }

                    @Override
                    public void onNext(User user) {

                    }
                });
    }
    //endregion
}
