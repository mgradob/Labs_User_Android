package com.itesm.labs.labsuser.app.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.AllInventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.DetailRequestFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersFragment;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.commons.adapters.CartRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.UserCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.ComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.adapters.RecordRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.services.BackgroundService;
import com.itesm.labs.labsuser.app.commons.views.activities.LaboratoriesActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.squareup.otto.Bus;

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
                BaseRecyclerAdapter.class,
                BackgroundService.class,
                BaseRecyclerAdapter.class,
                LabsRecyclerAdapter.class,
                UserCategoryRecyclerAdapter.class,
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

    @Provides
    @Singleton
    Bus providesEventBus(){
        return new Bus();
    }
    //endregion

    //region Fragments
    @Provides
    @Singleton
    RequestsControllerFragment providesRequestsFragment() {
        return new RequestsControllerFragment();
    }

    @Provides
    @Singleton
    DetailRequestFragment providesRequestDetailFragment() {
        return new DetailRequestFragment();
    }

    @Provides
    @Singleton
    AllInventoryFragment providesInventoryFragment() {
        return new AllInventoryFragment();
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
