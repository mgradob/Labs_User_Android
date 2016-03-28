package com.itesm.labs.labsuser.app.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.labs.labsuser.app.admin.adapters.AdminCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.dialogs.EditUserDialog;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryDetailFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.inventory.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsDetailFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersControllerFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersDetailFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.users.UsersFragment;
import com.itesm.labs.labsuser.app.admin.views.presenters.inventory.InventoryDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.inventory.InventoryPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.requests.RequestsDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.requests.RequestsPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.users.UsersDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.users.UsersPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.bases.BaseDialogFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.utils.NfcHandler;
import com.itesm.labs.labsuser.app.commons.views.activities.LabsActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.itesm.labs.labsuser.app.commons.views.presenters.LabsActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.LoginActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.MainActivityPresenter;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by mgradob on 10/26/15.
 */
@Module(
        complete = false,
        library = true,
        injects = {
                LabsApp.class,
                BaseActivity.class,
                BaseFragment.class,
                LoginActivity.class,
                LabsActivity.class,
                MainActivity.class,
                BaseDialogFragment.class,
                EditUserDialog.class,
                BaseRecyclerAdapter.class,
                BaseViewHolder.class,
                LabsRecyclerAdapter.class,
                AdminSectionPagerAdapter.class,
                AdminCategoryRecyclerAdapter.class,
                AdminComponentRecyclerAdapter.class,
                AdminRequestDetailRecyclerAdapter.class,
                AdminRequestRecyclerAdapter.class,
                AdminUserDetailRecyclerAdapter.class,
                AdminUserRecyclerAdapter.class,
                BaseActivityPresenter.class,
                BaseFragmentPresenter.class,
                LoginActivityPresenter.class,
                LabsActivityPresenter.class,
                MainActivityPresenter.class,
                RequestsControllerFragment.class,
                InventoryControllerFragment.class,
                UsersControllerFragment.class,
                RequestsFragment.class,
                RequestsPresenter.class,
                RequestsDetailFragment.class,
                RequestsDetailPresenter.class,
                InventoryFragment.class,
                InventoryPresenter.class,
                InventoryDetailFragment.class,
                InventoryDetailPresenter.class,
                UsersFragment.class,
                UsersPresenter.class,
                UsersDetailFragment.class,
                UsersDetailPresenter.class,
                ReportsFragment.class,
                NfcHandler.class
        }
)
public class AppModule {

    private Context context;

    public AppModule(LabsApp app) {
        super();
        this.context = app.getApplicationContext();
    }

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
    Bus providesEventBus() {
        return new Bus(ThreadEnforcer.MAIN);
    }

    @Provides
    Subscription providesSubscription() {
        return Subscriptions.empty();
    }

    @Provides
    @Singleton
    RequestsControllerFragment providesRequestsControllerFragment() {
        return new RequestsControllerFragment();
    }

    @Provides
    @Singleton
    InventoryControllerFragment providesInventoryControllerFragment() {
        return new InventoryControllerFragment();
    }

    @Provides
    @Singleton
    UsersControllerFragment providesUsersControllerFragment() {
        return new UsersControllerFragment();
    }

    @Provides
    @Singleton
    ReportsFragment providesReportsFragment() {
        return new ReportsFragment();
    }
}
