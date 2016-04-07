package com.itesm.labs.labsuser.app.commons.views.presenters;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.util.Log;

import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.itesm.labs.labsuser.app.commons.utils.NfcHandler;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;

/**
 * Created by mgradob on 2/21/16.
 */
public class MainActivityPresenter extends BaseActivityPresenter {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();

    private MainActivity mView;
    private NfcAdapter mNfcAdapter;
    private NfcHandler mNfcHandler;

    public MainActivityPresenter(MainActivity mView) {
        super();
        this.mView = mView;
    }

    public void setupNfc() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(mView);

        if (mNfcAdapter == null) Log.d(TAG, "No NFC adapter available");

        if(!mNfcAdapter.isEnabled()){
            Log.d(TAG, "NFC adapter is disabled");
            mView.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }

        mNfcHandler = new NfcHandler();
    }

    public void enableNfc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mNfcAdapter.enableReaderMode(mView, mNfcHandler, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B, null);
    }

    public void disableNfc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mNfcAdapter.disableReaderMode(mView);
    }
}
