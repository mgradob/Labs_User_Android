package com.itesm.labs.labsuser.app.bases;

import android.content.Context;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by mgradob on 2/17/16.
 */
public class BaseFragmentPresenter {

    @Inject
    protected Context mContext;
    @Inject
    protected LabsPreferences mLabsPreferences;
    @Inject
    protected Subscription mSubscription;

    protected BaseFragmentPresenter() {
        LabsApp.get().inject(this);
    }
}
