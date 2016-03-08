package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 3/7/16.
 */
public abstract class BaseControllerFragment extends Fragment {

    @Inject
    public Context mContext;

    public FragmentManager mFragmentManager;

    public BaseControllerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
