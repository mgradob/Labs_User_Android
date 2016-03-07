package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.commons.utils.FragmentState;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mgradob on 10/31/15.
 */
public abstract class BaseFragment extends Fragment {

    @Inject
    public Context mContext;
    @Inject
    public Bus mEventBus;

    public FragmentState mFragmentState = FragmentState.ITEMS_ALL;

    private boolean isBusRegistered = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        registerBus();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(hidden) unregisterBus();
    }

    @Override
    public void onPause() {
        unregisterBus();

        super.onPause();
    }

    public abstract void setupUi();

    public abstract void updateAll(List list);

    public abstract void updateDetails(List list);

    public abstract void showError();

    private void registerBus() {
        if (!isBusRegistered) {
            mEventBus.register(this);
            isBusRegistered = true;
        }
    }

    private void unregisterBus() {
        if (isBusRegistered) {
            mEventBus.unregister(this);
            isBusRegistered = false;
        }
    }
}
