package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.commons.utils.SnackbarUtil;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by mgradob on 10/31/15.
 */
public abstract class LabsBaseFragment extends Fragment {

    @Inject
    public LabsPreferences mLabsPreferences;

    @Inject
    public Context mContext;

    @Inject
    public Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
