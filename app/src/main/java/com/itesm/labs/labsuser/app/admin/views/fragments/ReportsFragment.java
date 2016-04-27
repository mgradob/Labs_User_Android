package com.itesm.labs.labsuser.app.admin.views.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;

import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends BaseFragment implements IListContract {


    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupUi() {

    }

    @Override
    public void setupList() {

    }

    @Override
    public void setupRefresh() {

    }

    @Override
    public void updateInfo(List data) {

    }

    @Override
    public void showError(ErrorType error) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}