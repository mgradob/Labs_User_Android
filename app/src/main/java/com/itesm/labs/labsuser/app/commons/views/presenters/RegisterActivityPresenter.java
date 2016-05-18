package com.itesm.labs.labsuser.app.commons.views.presenters;

import android.util.Log;
import android.widget.Toast;

import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.activities.RegisterActivity;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.NewUser;

import javax.inject.Inject;

import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 5/2/16.
 */
public class RegisterActivityPresenter extends BaseActivityPresenter {

    private static final String TAG = RegisterActivityPresenter.class.getSimpleName();

    @Inject
    UserClient mUserClient;

    private RegisterActivity mView;

    public RegisterActivityPresenter(RegisterActivity mView) {
        super();

        this.mView = mView;
    }

    public void registerUser(String userId, String userName, String userLastName1, String userLastName2, String userCareer, String userPassword) {
        NewUser newUser = new NewUser(userId, userName, userLastName1, userLastName2, userCareer, userPassword, userId + "@itesm.mx", mLabsPreferences.getUserAllowedLabs());

        mSubscription.unsubscribe();
        mSubscription = mUserClient.registerUser(newUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task register user started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task register user completed");
                        Toast.makeText(mContext, "Usuario creado", Toast.LENGTH_LONG)
                                .show();
                        mView.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task register user error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
