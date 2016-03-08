package com.itesm.labs.labsuser.app.admin.views.fragments.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseControllerFragment;
import com.itesm.labs.labsuser.app.commons.utils.IFragmentCallback;
import com.mgb.labsapi.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 3/7/16.
 */
public class UsersControllerFragment extends BaseControllerFragment implements IFragmentCallback<User> {

    private static final String TAG = UsersControllerFragment.class.getSimpleName();

    private static final String BACKSTACK_USERS_DETAIL = "USERS_DETAIL";

    @Bind(R.id.users_controller_container)
    FrameLayout mContainer;

    UsersFragment mUsersFragment;
    UsersDetailFragment mUsersDetailFragment;

    // Required empty contructor
    public UsersControllerFragment() {
    }

    public static UsersControllerFragment newInstance() {
        return new UsersControllerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_controller, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentManager = getFragmentManager();

        mUsersFragment = UsersFragment.newInstance(this);
        mFragmentManager.beginTransaction()
                .replace(R.id.users_controller_container, mUsersFragment)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onListItemClicked(User data) {
        mUsersDetailFragment = UsersDetailFragment.newInstance(data);

        mFragmentManager.beginTransaction()
                .replace(R.id.users_controller_container, mUsersDetailFragment)
                .addToBackStack(BACKSTACK_USERS_DETAIL)
                .commit();
    }
}
