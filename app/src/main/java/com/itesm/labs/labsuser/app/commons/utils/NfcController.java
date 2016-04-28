package com.itesm.labs.labsuser.app.commons.utils;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by mgradob on 4/27/16.
 */
public class NfcController {

    private static final String TAG = NfcController.class.getSimpleName();

    @Inject
    NfcHandler mNfcHandler;

    private NfcAdapter mNfcAdapter;

    public void setupNfc(Activity activity) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);

        if (mNfcAdapter == null) {
            Log.d(TAG, "No NFC adapter available");
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Log.d(TAG, "NFC adapter is disabled");
            activity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    public void enableNfc(Activity activity) {
        if (mNfcAdapter == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mNfcAdapter.enableReaderMode(activity, mNfcHandler, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_NFC_B, null);
    }

    public void disableNfc(Activity activity) {
        if (mNfcAdapter == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mNfcAdapter.disableReaderMode(activity);
    }
}
