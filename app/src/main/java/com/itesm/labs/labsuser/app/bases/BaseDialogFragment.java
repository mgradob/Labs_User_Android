package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.application.LabsPreferences;

import javax.inject.Inject;

/**
 * Created by mgradob on 2/24/16.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Inject
    protected Context mContext;
    @Inject
    protected LabsPreferences mLabsPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
