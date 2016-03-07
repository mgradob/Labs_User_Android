package com.itesm.labs.labsuser.app.admin.views.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseDialogFragment;

import butterknife.ButterKnife;

/**
 * Created by mgradob on 2/26/16.
 */
public class EditUserDialog extends BaseDialogFragment {

    public EditUserDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_test, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
