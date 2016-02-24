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
import com.itesm.labs.labsuser.app.admin.views.fragments.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.admin.views.presenters.InventoryFragmentPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsFragmentPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.views.activities.LabsActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.itesm.labs.labsuser.app.commons.views.presenters.LabsActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.LoginActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.MainActivityPresenter;
import com.squareup.otto.Bus;

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
                RequestsFragment.class,
                RequestsFragmentPresenter.class,
                InventoryFragment.class,
                InventoryFragmentPresenter.class,
                UsersFragment.class,
                UsersFragmentPresenter.class,
                ReportsFragment.class
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
    Bus providesEventBus() {
        return new Bus();
    }

    @Provides
    Subscription providesSubscription() {
        return Subscriptions.empty();
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
