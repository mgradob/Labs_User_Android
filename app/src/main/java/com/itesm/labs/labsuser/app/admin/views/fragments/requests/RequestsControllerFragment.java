package com.itesm.labs.labsuser.app.admin.views.fragments.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.bases.BaseControllerFragment;
import com.itesm.labs.labsuser.app.commons.utils.IFragmentCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 3/6/16.
 */
public class RequestsControllerFragment extends BaseControllerFragment implements IFragmentCallback<ItemUserCart> {

    private static final String TAG = RequestsControllerFragment.class.getSimpleName();

    private static final String BACKSTACK_REQUEST_DETAIL = "REQUEST_DETAIL";

    @Bind(R.id.requests_controller_container)
    FrameLayout mContainer;

    RequestsFragment mRequestsFragment;
    RequestsDetailFragment mRequestsDetailFragment;

    // Required empty constructor
    public RequestsControllerFragment() {
    }

    public static RequestsControllerFragment newInstance() {
        return new RequestsControllerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests_controller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentManager = getFragmentManager();

        mRequestsFragment = RequestsFragment.newInstance(this);
        mFragmentManager.beginTransaction()
                .replace(R.id.requests_controller_container, mRequestsFragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onListItemClicked(ItemUserCart data) {
        mRequestsDetailFragment = RequestsDetailFragment.newInstance(data);

        mFragmentManager.beginTransaction()
                .replace(R.id.requests_controller_container, mRequestsDetailFragment)
                .addToBackStack(BACKSTACK_REQUEST_DETAIL)
                .commit();
    }
}
