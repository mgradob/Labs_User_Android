package com.itesm.labs.labsuser.app.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.labs.labsuser.app.activities.LaboratoriesActivity;
import com.itesm.labs.labsuser.app.activities.LoginActivity;
import com.itesm.labs.labsuser.app.activities.MainActivity;
import com.itesm.labs.labsuser.app.adapters.CartRecyclerAdapter;
import com.itesm.labs.labsuser.app.adapters.CategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.adapters.ComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.adapters.RecordRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.LabsBaseActivity;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.fragments.CartFragment;
import com.itesm.labs.labsuser.app.fragments.MaterialsFragment;
import com.itesm.labs.labsuser.app.fragments.RecordFragment;
import com.itesm.labs.labsuser.app.rest.clients.CartClient;
import com.itesm.labs.labsuser.app.rest.clients.CategoryClient;
import com.itesm.labs.labsuser.app.rest.clients.ComponentClient;
import com.itesm.labs.labsuser.app.rest.clients.LaboratoryClient;
import com.itesm.labs.labsuser.app.rest.clients.RecordClient;
import com.itesm.labs.labsuser.app.rest.clients.UserClient;
import com.itesm.labs.labsuser.app.rest.services.CartService;
import com.itesm.labs.labsuser.app.rest.services.CategoryService;
import com.itesm.labs.labsuser.app.rest.services.ComponentService;
import com.itesm.labs.labsuser.app.rest.services.LaboratoryService;
import com.itesm.labs.labsuser.app.rest.services.RecordService;
import com.itesm.labs.labsuser.app.rest.services.UserService;
import com.itesm.labs.labsuser.app.services.BackgroundService;
import com.itesm.labs.labsuser.app.utils.SnackbarUtil;

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
    AppGlobals providesAppGlobals() {
        return new AppGlobals();
    }
    //endregion

    //region Rest Clients
    @Provides
    @Singleton
    UserClient providesUserClient(UserService userService) {
        return new UserClient(userService);
    }

    @Provides
    @Singleton
    LaboratoryClient providesLaboratoryClient(LaboratoryService laboratoryService) {
        return new LaboratoryClient(laboratoryService);
    }

    @Provides
    @Singleton
    CategoryClient providesCategoryClient(CategoryService categoryService) {
        return new CategoryClient(categoryService);
    }

    @Provides
    @Singleton
    ComponentClient providesComponentClient(ComponentService componentService) {
        return new ComponentClient(componentService);
    }

    @Provides
    @Singleton
    CartClient providesCartClient(CartService cartService, ComponentService componentService) {
        return new CartClient(cartService, componentService);
    }

    @Provides
    @Singleton
    RecordClient providesRecordClient(RecordService recordService, CategoryService categoryService, ComponentService componentService) {
        return new RecordClient(componentService, categoryService, recordService);
    }
    //endregion
}
