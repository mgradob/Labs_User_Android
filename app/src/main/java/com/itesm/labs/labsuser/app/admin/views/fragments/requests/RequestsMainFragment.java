package com.itesm.labs.labsuser.app.admin.views.fragments.requests;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.callbacks.RequestsCallbacks;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * A {@link LabsBaseFragment} that handles the flow between the users requests list and the detail
 * view for each request.
 */
public class RequestsMainFragment extends LabsBaseFragment implements RequestsCallbacks {

    private final String TAG = RequestsMainFragment.class.getSimpleName();

    @Inject
    AllRequestsFragment mAllRequestsFragment;
    @Inject
    DetailRequestFragment mDetailRequestFragment;

    private DETAIL_STATE fragmentState = DETAIL_STATE.ALL_REQUESTS;

    public DETAIL_STATE getFragmentState() {
        return fragmentState;
    }

    public RequestsMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupFragmentInfo();
    }

    private void setupFragmentInfo() {
        mAllRequestsFragment = new AllRequestsFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_requests_main_container, mAllRequestsFragment)
                .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                .commit();

        fragmentState = DETAIL_STATE.ALL_REQUESTS;
    }

    @Override
    public void goToRequestDetail(String userId) {
        mDetailRequestFragment = DetailRequestFragment.newInstance(userId);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_requests_main_container, mDetailRequestFragment)
                .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                .commit();

        fragmentState = DETAIL_STATE.REQUEST_DETAIL;
    }

    private enum DETAIL_STATE {
        ALL_REQUESTS(0),
        REQUEST_DETAIL(1);

        DETAIL_STATE(int i) {

        }
    }
}
