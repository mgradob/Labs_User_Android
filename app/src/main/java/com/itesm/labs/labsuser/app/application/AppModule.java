package com.itesm.labs.labsuser.app.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.labs.labsuser.app.admin.views.fragments.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestDetailFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.commons.adapters.CartRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.CategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.ComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.RecordRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.services.BackgroundService;
import com.itesm.labs.labsuser.app.commons.views.activities.LaboratoriesActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.itesm.labs.labsuser.app.commons.views.fragments.CartFragment;
import com.itesm.labs.labsuser.app.commons.views.fragments.MaterialsFragment;
import com.itesm.labs.labsuser.app.commons.views.fragments.RecordFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mgradob on 10/26/15.
 */
@Module(
        complete = false,
        library = true,
        injects = {
                LabsApp.class,
                LabsBaseActivity.class,
                LabsBaseFragment.class,
                LoginActivity.class,
                LaboratoriesActivity.class,
                MainActivity.class,
                MaterialsFragment.class,
                CartFragment.class,
                RecordFragment.class,
                BaseRecyclerAdapter.class,
                BackgroundService.class,
                BaseRecyclerAdapter.class,
                LabsRecyclerAdapter.class,
                CategoryRecyclerAdapter.class,
                ComponentRecyclerAdapter.class,
                CartRecyclerAdapter.class,
                RecordRecyclerAdapter.class
        }
)
public class AppModule {

    private Context context;

    public AppModule(LabsApp app) {
        super();
        this.context = app.getApplicationContext();
    }

    //region Global Objects
    @Provides
    @Singleton
    Context providesApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(AppConstants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    LabsPreferences providesLabsPreferences(SharedPreferences sharedPreferences) {
        return new LabsPreferences(sharedPreferences);
    }
    //endregion

    //region Fragments
    @Provides
    @Singleton
    RequestsFragment providesRequestsFragment() {
        return new RequestsFragment();
    }

    @Provides
    @Singleton
    RequestDetailFragment providesRequestDetailFragment() {
        return new RequestDetailFragment();
    }

    @Provides
    @Singleton
    InventoryFragment providesInventoryFragment() {
        return new InventoryFragment();
    }

    @Provides
    @Singleton
    UsersFragment providesUsersFragment() {
        return new UsersFragment();
    }

    @Provides
    @Singleton
    ReportsFragment providesReportsFragment() {
        return new ReportsFragment();
    }
    //endregion
}
