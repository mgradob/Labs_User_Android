package com.itesm.labs.labsuser.app.commons.utils;

import android.annotation.TargetApi;
import android.nfc.NfcAdapter.ReaderCallback;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by mgradob on 1/22/15.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class NfcHandler implements ReaderCallback {

    private static final String TAG = NfcHandler.class.getSimpleName();

    @Inject
    Bus mEventBus;

    public NfcHandler() {
        LabsApp.get().inject(this);
    }

    public static long bytesToLong(byte[] data) {
        long tag = 0;
        for (byte mByte : data) {

            tag = (tag << 8) | (mByte & 0xFF);
        }

        return tag;
    }

    /**
     * Convenience method to convert a byte array to a hex string.
     *
     * @param data the byte[] to convert
     * @return String the converted byte[]
     */
    public static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(byteToHex(data[i]).toUpperCase());
        }
        return (buf.toString());
    }

    /**
     * method to convert a byte to a hex string.
     *
     * @param data the byte to convert
     * @return String the converted byte
     */
    public static String byteToHex(byte data) {
        StringBuffer buf = new StringBuffer();
        buf.append(toHexChar((data >> 4) & 0x0F));
        buf.append(toHexChar(data & 0x0F));
        return buf.toString();
    }

    /**
     * Convenience method to convert an int to a hex char.
     *
     * @param i the int to convert
     * @return char the converted char
     */
    public static char toHexChar(int i) {
        if ((0 <= i) && (i <= 9)) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + (i - 10));
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, "Detected UID: " + bytesToLong(tag.getId()));
        mEventBus.post(new UIDEvent(bytesToLong(tag.getId())));
    }
}
