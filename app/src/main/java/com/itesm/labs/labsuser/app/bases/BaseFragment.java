package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mgradob on 10/31/15.
 */
public abstract class BaseFragment extends Fragment {

    @Inject
    protected Context mContext;
    @Inject
    protected Bus mEventBus;

    protected BaseRecyclerAdapter mAdapter;

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

        if (hidden) unregisterBus();
    }

    @Override
    public void onPause() {
        unregisterBus();

        super.onPause();
    }

    /**
     * Sets up the UI for the fragment.
     */
    protected abstract void setupUi();

    /**
     * Display a {@link android.support.design.widget.Snackbar} when an error occurs.
     */
    protected abstract void showError(ErrorType error);

    /**
     * Register the fragment to the event bus.
     */
    private void registerBus() {
        if (!isBusRegistered) {
            mEventBus.register(this);
            isBusRegistered = true;
        }
    }

    /**
     * Unregister the fragment from the event bus.
     */
    private void unregisterBus() {
        if (isBusRegistered) {
            mEventBus.unregister(this);
            isBusRegistered = false;
        }
    }
}
