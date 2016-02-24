package com.itesm.labs.labsuser.app.bases;

import android.content.Context;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by mgradob on 2/21/16.
 */
public abstract class BaseActivityPresenter {

    @Inject
    public Context mContext;
    @Inject
    public LabsPreferences mLabsPreferences;
    @Inject
    public Subscription mSubscription;

    public BaseActivityPresenter() {
        LabsApp.get().inject(this);
    }
}
