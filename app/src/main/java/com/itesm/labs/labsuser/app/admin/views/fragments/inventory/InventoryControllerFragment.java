package com.itesm.labs.labsuser.app.admin.views.fragments.inventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;

/**
 * Created by mgradob on 2/12/16.
 */
public class InventoryControllerFragment extends LabsBaseFragment {

    private static final String TAG = InventoryControllerFragment.class.getSimpleName();

    public InventoryControllerFragment() {
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //endregion
}
