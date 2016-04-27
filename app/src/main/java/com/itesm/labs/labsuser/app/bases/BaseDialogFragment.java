package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;

import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 2/24/16.
 */
public abstract class BaseDialogFragment extends BottomSheetDialogFragment {

    @Inject
    protected Context mContext;

    public BaseDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LabsApp.get().inject(this);
    }
}
