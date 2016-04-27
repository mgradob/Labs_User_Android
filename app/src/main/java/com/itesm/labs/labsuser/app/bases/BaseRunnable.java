package com.itesm.labs.labsuser.app.bases;

import android.content.Context;

import com.itesm.labs.labsuser.app.application.LabsPreferences;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by mgradob on 12/27/15.
 */
public abstract class BaseRunnable implements Runnable {

    @Inject
    protected Context mContext;
    @Inject
    protected LabsPreferences mLabsPreferences;

    protected Subscription mSubscription;

    @Override
    public void run() {
    }
}
