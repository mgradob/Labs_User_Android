package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itesm.labs.labsuser.app.application.AppGlobals;
import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 10/26/15.
 */
public abstract class LabsBaseActivity extends AppCompatActivity {

    @Inject
    public Context mContext;

    @Inject
    public AppGlobals mAppGlobals;

    @Inject
    public SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
