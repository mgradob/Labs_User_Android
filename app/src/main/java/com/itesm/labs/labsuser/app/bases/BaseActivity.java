package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

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
}
