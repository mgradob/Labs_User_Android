package com.itesm.labs.labsuser.app.application;

import android.app.Application;

import dagger.ObjectGraph;

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
