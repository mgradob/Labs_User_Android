package com.itesm.labs.labsuser.app.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.itesm.labs.labsuser.BuildConfig;
import com.mgb.labsapi.clients.UserClient;

import javax.inject.Inject;

import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import rx.Subscription;

/**
 * Created by mgradob on 10/26/15.
 */
public class LabsApp extends Application {

    private static LabsApp mAppContext;
    private ObjectGraph mObjectGraph;

    public static LabsApp get() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.ENABLE_FABRIC) Fabric.with(this, new Crashlytics());

        mAppContext = (LabsApp) getApplicationContext();

        setupObjectGraph();
    }

    private void setupObjectGraph() {
        Object[] modules = Modules.modulesForApp(this);
        mObjectGraph = ObjectGraph.create(modules);
    }

    public void inject(Object obj) {
        mObjectGraph.inject(obj);
    }
}
