package com.itesm.labs.labsuser.app.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.activities.MainActivity;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.application.AppGlobals;
import com.itesm.labs.labsuser.app.rest.clients.CartClient;
import com.itesm.labs.labsuser.app.rest.models.CartItem;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by mgradob on 9/9/15.
 */
public class BackgroundService extends Service {

    private static final int POLL_TIME = 1000 * 60 * 1;
    private static final int NOTIFICATION_ID = 1;

    @Inject
    CartClient mCartClient;
    @Inject
    AppGlobals mAppGlobals;
    @Inject
    SharedPreferences mSharedPreferences;

    private String TAG = BackgroundService.class.getSimpleName();
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
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Running runnable");

                getUserCart();

                mHandler.postDelayed(mRunnable, POLL_TIME);
            }
        };

        mHandler.postDelayed(mRunnable, POLL_TIME);
    }

    private void stopPolling() {
        Log.d(TAG, "Stop polling");
        mHandler.removeCallbacks(mRunnable);
    }

    private void showNotification() {
        Log.d(TAG, "Showing notification");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(getResources().getString(R.string.notification_intent_action), true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_notification_small);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_herramientas));
        builder.setContentTitle(getResources().getString(R.string.notification_cart_ready_title));
        builder.setContentText(getResources().getString(R.string.notification_cart_ready_body));
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = (NotificationManagerCompat) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void getUserCart() {
        mCartClient.getCartItemsOf(mSharedPreferences.getString(AppConstants.PREFERENCES_KEY_USER_TOKEN, ""),
                mAppGlobals.getLabLink(), mAppGlobals.getUser().getUserId())
                .subscribeOn(Schedulers.immediate())
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
