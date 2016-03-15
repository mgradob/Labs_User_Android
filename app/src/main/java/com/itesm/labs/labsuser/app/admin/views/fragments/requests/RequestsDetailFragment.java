package com.itesm.labs.labsuser.app.admin.views.fragments.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCartDetail;
import com.itesm.labs.labsuser.app.admin.views.presenters.requests.RequestsDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseFragment;
import com.itesm.labs.labsuser.app.commons.events.SnackbarEvent;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.mgb.labsapi.models.CartItem;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by mgradob on 3/6/16.
 */
public class RequestsDetailFragment extends BaseFragment {

    private static final String TAG = RequestsControllerFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_requests_detail_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fragment_requests_detail_fab)
    FloatingActionButton mFab;

    private RequestsDetailPresenter mPresenter;
    private ItemUserCart mUserCart;

    private long mUserUid;

    // Required empty constructor.
    public RequestsDetailFragment() {
    }

    public static RequestsDetailFragment newInstance(ItemUserCart cart) {
        RequestsDetailFragment fragment = new RequestsDetailFragment();
        fragment.mUserCart = cart;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);

        mPresenter = new RequestsDetailPresenter(this, mUserCart);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUi();

        mPresenter.getRequestDetail();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setupUi() {
        setupList();
        setupRefresh();
    }

    @Override
    public void setupList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mAdapter = new AdminRequestDetailRecyclerAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setupRefresh() {
        mRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.getRequestDetail());
    }

    public void setupFab() {
    }

    @Override
    public void updateInfo(List data) {
        mRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);

        ItemUserCartDetail detail = (ItemUserCartDetail) data.get(0);

        if (detail.getCartItem().isReady()) mFab.setImageResource(R.drawable.ic_uid_white);
        else mFab.setImageResource(R.drawable.ic_done_white);
    }

    @Override
    public void showError(ErrorType error) {

    }

    @Subscribe
    public void onUIDEvent(UIDEvent event) {
        if (event != null) mUserUid = event.getUID();
    }

    @OnClick(R.id.fragment_requests_detail_fab)
    void onValidateWithUid() {
        if(!mUserCart.isReady()) mPresenter.readyUserCart();
        else mPresenter.validateUserCart(mUserUid, false);
    }

    @OnLongClick(R.id.fragment_requests_detail_fab)
    boolean onForceValidate() {
        mPresenter.validateUserCart(mUserUid, true);
        return true;
    }

    public void showValidationSuccess() {
        mEventBus.post(new SnackbarEvent(R.string.request_item_validate_success));
    }

    public void showValidationError() {
        mEventBus.post(new SnackbarEvent(R.string.request_item_validate_error));
    }
}
