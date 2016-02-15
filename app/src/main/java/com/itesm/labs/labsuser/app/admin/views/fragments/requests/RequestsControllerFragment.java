package com.itesm.labs.labsuser.app.admin.views.fragments.requests;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.events.GoToRequestDetailsEvent;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.itesm.labs.labsuser.app.commons.events.BackPressedEvent;
import com.itesm.labs.labsuser.app.commons.events.FinishActivityEvent;
import com.itesm.labs.labsuser.app.commons.utils.FragmentState;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * A {@link LabsBaseFragment} that handles the flow between the users requests list and the detail
 * view for each request.
 */
public class RequestsControllerFragment extends LabsBaseFragment {

    private static final String TAG = RequestsControllerFragment.class.getSimpleName();

    @Inject
    AllRequestsFragment mAllRequestsFragment;
    @Inject
    DetailRequestFragment mDetailRequestFragment;

    private FragmentState fragmentState = FragmentState.ALL_ITEMS;

    public RequestsControllerFragment() {
        // Required empty public constructor
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mEventBus.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests_controller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupFragmentInfo();
    }
    //endregion

    //region Fragment Setup
    private void setupFragmentInfo() {
        mAllRequestsFragment = new AllRequestsFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_requests_main_container, mAllRequestsFragment)
                .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                .commit();

        fragmentState = FragmentState.ALL_ITEMS;
    }
    //endregion

    //region Event Bus
    @Subscribe
    public void onGoToRequestDetailEvent(GoToRequestDetailsEvent event) {
        mDetailRequestFragment = DetailRequestFragment.newInstance(event.getUserId());

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_requests_main_container, mDetailRequestFragment)
                .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                .commit();

        fragmentState = FragmentState.DETAIL_ITEMS;
    }

    @Subscribe
    public void onBackPressedEvent(BackPressedEvent event) {
        if (fragmentState == FragmentState.DETAIL_ITEMS) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_requests_main_container, mAllRequestsFragment)
                    .setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)
                    .commit();

            fragmentState = FragmentState.ALL_ITEMS;
        } else
            mEventBus.post(new FinishActivityEvent());
    }
    //endregion
}
