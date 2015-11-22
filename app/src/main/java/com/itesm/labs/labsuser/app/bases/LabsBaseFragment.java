package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.itesm.labs.labsuser.app.application.AppGlobals;
import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.utils.SnackbarUtil;

import javax.inject.Inject;

/**
 * Created by mgradob on 10/31/15.
 */
public abstract class LabsBaseFragment extends Fragment {

    @Inject
    public SharedPreferences mSharedPreferences;

    @Inject
    public AppGlobals mAppGlobals;

    @Inject
    public Context mContext;

    public SnackbarUtil mSnackbarUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
