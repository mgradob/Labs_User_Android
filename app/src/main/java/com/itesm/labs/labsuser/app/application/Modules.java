package com.itesm.labs.labsuser.app.application;

import com.itesm.labs.labsuser.BuildConfig;
import com.mgb.labsapi.ApiModule;

/**
 * Created by mgradob on 10/25/15.
 */
public class Modules {

    private static Modules modules;

    private Modules() {
    }

    public static Modules instance() {
        if (modules == null) {
            modules = new Modules();
        }
        return modules;
    }

    public static Object[] modulesForApp(LabsApp app) {
        return new Object[]{
                new ApiModule(BuildConfig.BASE_API_URL),
                new AppModule(app)
        };
    }
}
