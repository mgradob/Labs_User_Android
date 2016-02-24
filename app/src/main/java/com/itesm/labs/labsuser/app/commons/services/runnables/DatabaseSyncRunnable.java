//package com.itesm.labs.labsuser.app.commons.services.runnables;
//
//import android.util.Log;
//
//import com.itesm.labs.labsuser.app.bases.BaseRunnable;
//import com.itesm.labs.labsuser.app.rest.clients.ApiService;
//
//import javax.inject.Inject;
//
//import rx.Subscriber;
//import rx.schedulers.Schedulers;
//
///**
// * Created by mgradob on 12/27/15.
// */
//public class DatabaseSyncRunnable extends BaseRunnable {
//
//    private static final String TAG = DatabaseSyncRunnable.class.getSimpleName();
//
//    @Inject
//    ApiService mApiService;
//
//    @Override
//    public void run() {
//        Log.d(TAG, "Running...");
//        syncDatabaseInfo();
//    }
//
//    private void syncDatabaseInfo() {
//        if (!mLabsPreferences.getIsLogged()) return;
//
//        mSubscription.unsubscribe();
//        mSubscription = mApiService.syncDb(mLabsPreferences.getToken(),
//                mLabsPreferences.getCurrentLab().getLink(),
//                mLabsPreferences.getUserId())
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(new Subscriber<Void>() {
//                    @Override
//                    public void onStart() {
//                        Log.d(TAG, "Sync service started");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "Sync service completed");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "Sync service error: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Void aVoid) {
//
//                    }
//                });
//
//    }
//}
