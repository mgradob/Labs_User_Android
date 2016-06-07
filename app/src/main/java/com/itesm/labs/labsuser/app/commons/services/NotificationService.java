package com.itesm.labs.labsuser.app.commons.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.models.CartItem;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by mgradob on 9/9/15.
 */
public class NotificationService extends Service {

    private static final int POLL_TIME = 1000 * 60 * 5;
    private static final int NOTIFICATION_ID = 1;

    private static final String TAG = NotificationService.class.getSimpleName();

    @Inject
    CartClient mCartClient;
    @Inject
    LabsPreferences mLabsPreferences;
    @Inject
    Context mContext;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();

        LabsApp.get().inject(this);
    }

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

            getUserCart();

            mHandler.postDelayed(mRunnable, POLL_TIME);
        };

        mHandler.postDelayed(mRunnable, POLL_TIME);
    }

    private void stopPolling() {
        Log.d(TAG, "Stop polling");
        mHandler.removeCallbacks(mRunnable);
    }

    private void showNotification() {
        Log.d(TAG, "Showing notification");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_chemistry_white);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_herramientas));
        builder.setContentTitle(getResources().getString(R.string.notification_cart_ready_title));
        builder.setContentText(getResources().getString(R.string.notification_cart_ready_body));
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void getUserCart() {
        mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mLabsPreferences.getUserId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get user cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get user cart completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get user cart error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> cartItems) {
                        if (cartItems == null || cartItems.size() == 0)
                            throw new NullPointerException("Cart items is null or empty");

                        int readyQuantity = 0;

                        for (CartItem item : cartItems) {
                            if (item.isReady()) readyQuantity++;
                        }

                        if (readyQuantity == cartItems.size()) showNotification();
                    }
                });
    }
}
