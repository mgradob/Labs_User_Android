package com.itesm.labs.labsuser.app.commons.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mgradob on 5/30/16.
 */
public class ApiService extends Service {

    private static final int POLL_TIME = 1000 * 60 * 5;
    private static final String TAG = ApiService.class.getSimpleName();

    private Handler mHandler;
    private Runnable mRunnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        startPolling();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopPolling();
    }

    private void startPolling() {
        Log.d(TAG, "Start polling");
        mRunnable = () -> {
            Log.d(TAG, "Running runnable");

            mHandler.postDelayed(mRunnable, POLL_TIME);
        };

        mHandler.postDelayed(mRunnable, POLL_TIME);
    }

    private void stopPolling() {
        Log.d(TAG, "Stop polling");
        mHandler.removeCallbacks(mRunnable);
    }
}
