package com.itesm.labs.labsuser.app.bases;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 2/24/16.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Inject
    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
