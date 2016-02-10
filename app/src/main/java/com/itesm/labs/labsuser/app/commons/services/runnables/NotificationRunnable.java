package com.itesm.labs.labsuser.app.commons.services.runnables;

import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.AppConstants;
import com.itesm.labs.labsuser.app.bases.BaseRunnable;
import com.itesm.labs.labsuser.app.rest.clients.ApiService;
import com.mgb.labsapi.models.CartItem;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 12/27/15.
 */
public class NotificationRunnable extends BaseRunnable {

    private static final String TAG = NotificationRunnable.class.getSimpleName();

    @Inject
    ApiService mApiService;

    @Override
    public void run() {
        Log.d(TAG, "Running...");
        notifyCartReady();
    }

    private void notifyCartReady() {
        mApiService.getCartItemsOf(mLabsPreferences.getToken(),
                mLabsPreferences.getCurrentLab().getLink(),
                mLabsPreferences.getUserId())
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

    private void showNotification() {
        Log.d(TAG, "Showing notification");
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.putExtra(getResources().getString(R.string.notification_intent_action), true);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);

        builder.setSmallIcon(R.drawable.ic_notification_small);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_herramientas));
        builder.setContentTitle(mContext.getResources().getString(R.string.notification_cart_ready_title));
        builder.setContentText(mContext.getResources().getString(R.string.notification_cart_ready_body));
        builder.setAutoCancel(true);
//        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(AppConstants.NOTIF_ACTION_USER, builder.build());
    }
}