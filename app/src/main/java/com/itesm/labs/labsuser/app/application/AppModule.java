package com.itesm.labs.labsuser.app.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;

import com.itesm.labs.labsuser.app.admin.adapters.AdminCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminSectionPagerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.AdminUserRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.views.activities.InventoryDetailActivity;
import com.itesm.labs.labsuser.app.admin.views.activities.RequestDetailActivity;
import com.itesm.labs.labsuser.app.admin.views.activities.UserDetailActivity;
import com.itesm.labs.labsuser.app.admin.views.activities.UserEditActivity;
import com.itesm.labs.labsuser.app.admin.views.dialogs.EditUserDialog;
import com.itesm.labs.labsuser.app.admin.views.fragments.InventoryFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.ReportsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.admin.views.fragments.UsersFragment;
import com.itesm.labs.labsuser.app.admin.views.presenters.InventoryDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.InventoryPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UserEditPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersDetailPresenter;
import com.itesm.labs.labsuser.app.admin.views.presenters.UsersPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.bases.BaseDialogFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.adapters.LabsRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.utils.NfcController;
import com.itesm.labs.labsuser.app.commons.utils.NfcHandler;
import com.itesm.labs.labsuser.app.commons.views.activities.LabsActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.LoginActivity;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.itesm.labs.labsuser.app.commons.views.presenters.LabsActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.LoginActivityPresenter;
import com.itesm.labs.labsuser.app.commons.views.presenters.MainActivityPresenter;
import com.itesm.labs.labsuser.app.user.adapters.UserCartRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.adapters.UserCategoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.adapters.UserComponentRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.adapters.UserHistoryRecyclerAdapter;
import com.itesm.labs.labsuser.app.user.adapters.UserSectionPagerAdapter;
import com.itesm.labs.labsuser.app.user.views.activities.UserInventoryDetailActivity;
import com.itesm.labs.labsuser.app.user.views.activities.UserMainActivity;
import com.itesm.labs.labsuser.app.user.views.fragments.UserCartFragment;
import com.itesm.labs.labsuser.app.user.views.fragments.UserHistoryFragment;
import com.itesm.labs.labsuser.app.user.views.fragments.UserInventoryFragment;
import com.itesm.labs.labsuser.app.user.views.presenters.UserCartPresenter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserHistoryPresenter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserInventoryDetailPresenter;
import com.itesm.labs.labsuser.app.user.views.presenters.UserInventoryPresenter;
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
                RequestsFragment.class,
                RequestsPresenter.class,
                RequestDetailActivity.class,
                RequestsDetailPresenter.class,
                InventoryFragment.class,
                InventoryPresenter.class,
                InventoryDetailActivity.class,
                InventoryDetailPresenter.class,
                UsersFragment.class,
                UsersPresenter.class,
                UserDetailActivity.class,
                UsersDetailPresenter.class,
                ReportsFragment.class,
                UserEditActivity.class,
                UserEditPresenter.class,
                NfcHandler.class,
                UserMainActivity.class,
                UserSectionPagerAdapter.class,
                UserInventoryFragment.class,
                UserInventoryPresenter.class,
                UserInventoryDetailActivity.class,
                UserInventoryDetailPresenter.class,
                UserCategoryRecyclerAdapter.class,
                UserComponentRecyclerAdapter.class,
                UserCartFragment.class,
                UserCartPresenter.class,
                UserCartRecyclerAdapter.class,
                UserHistoryFragment.class,
                UserHistoryPresenter.class,
                UserHistoryRecyclerAdapter.class,
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

    @Provides
    @Singleton
    NfcHandler providesNfcHandler() {
        return new NfcHandler();
    }

    @Provides
    @Singleton
    NfcController providesNfcController() {
        return new NfcController();
    }

    @Provides
    @Singleton
    UserInventoryFragment providesUserInventoryFragment() {
        return new UserInventoryFragment();
    }

    @Provides
    @Singleton
    UserCartFragment providesUserCartFragment() {
        return new UserCartFragment();
    }

    @Provides
    @Singleton
    UserHistoryFragment providesUserHistoryFragment() {
        return new UserHistoryFragment();
    }
}
