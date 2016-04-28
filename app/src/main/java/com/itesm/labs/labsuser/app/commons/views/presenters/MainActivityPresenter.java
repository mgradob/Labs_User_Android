package com.itesm.labs.labsuser.app.commons.views.presenters;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.util.Log;

import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.utils.NfcController;
import com.itesm.labs.labsuser.app.commons.utils.NfcHandler;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;

import javax.inject.Inject;

/**
 * Created by mgradob on 2/21/16.
 */
public class MainActivityPresenter extends BaseActivityPresenter {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();

    @Inject
    NfcController mNfcController;

    private MainActivity mView;


    public MainActivityPresenter(MainActivity mView) {
        super();
        this.mView = mView;
    }

    public void setupNfc() {
        mNfcController.setupNfc(mView);
    }

    public void enableNfc() {
        mNfcController.enableNfc(mView);
    }

    public void disableNfc() {
        mNfcController.disableNfc(mView);
    }
}
