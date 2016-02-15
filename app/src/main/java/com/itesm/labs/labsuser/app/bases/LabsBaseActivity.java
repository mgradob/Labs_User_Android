package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by mgradob on 10/26/15.
 */
public abstract class LabsBaseActivity extends AppCompatActivity {

    @Inject
    public Context mContext;
    @Inject
    public LabsPreferences mLabsPreferences;
    @Inject
    public Bus mEventBus;

    private MaterialDialog mDialog;
    public Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);

        super.onDestroy();
    }

    public void showMaterialDialog(int titleResource, int contentResource) {
        mDialog = new MaterialDialog.Builder(LabsApp.get())
                .title(getResources().getString(titleResource))
                .content(getResources().getString(contentResource))
                .progress(true, 100)
                .show();
    }

    public void dismissMaterialDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
