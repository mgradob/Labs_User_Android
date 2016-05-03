package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.mgb.labsapi.clients.UserClient;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 10/26/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    public Context mContext;
    @Inject
    public LabsPreferences mLabsPreferences;
    @Inject
    public Bus mEventBus;
    @Inject
    public Subscription mSubscription;
    @Inject
    UserClient mUserClient;

    private boolean isBusRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerBus();
    }

    @Override
    protected void onPause() {
        unregisterBus();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);

        super.onDestroy();
    }

    public abstract void setupUi();

    private void registerBus() {
        if (!isBusRegistered) mEventBus.register(this);
        isBusRegistered = true;
    }

    private void unregisterBus() {
        if (isBusRegistered) mEventBus.unregister(this);
        isBusRegistered = false;
    }

    public void setupStatusBar(int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(colorRes);
    }

    public void logoutUser() {
        mSubscription.unsubscribe();
        mSubscription = mUserClient.logoutUser(mLabsPreferences.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        mLabsPreferences.logout();

        Intent resetIntent = new Intent(mContext, LoginActivity.class);
        resetIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(resetIntent);
    }
}
