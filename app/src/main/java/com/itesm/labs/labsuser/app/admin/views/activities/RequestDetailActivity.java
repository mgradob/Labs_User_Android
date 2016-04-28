package com.itesm.labs.labsuser.app.admin.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.AdminRequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCartDetail;
import com.itesm.labs.labsuser.app.admin.views.presenters.RequestsDetailPresenter;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.commons.contracts.IListContract;
import com.itesm.labs.labsuser.app.commons.events.UIDEvent;
import com.itesm.labs.labsuser.app.commons.utils.ErrorType;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class RequestDetailActivity extends BaseActivity implements IListContract {

    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_IS_READY = "IS_READY";

    private static final String TAG = RequestDetailActivity.class.getSimpleName();
    private static long mUserUid;

    @Bind(R.id.activity_request_detail_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.activity_request_detail_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.activity_request_detail_fab)
    FloatingActionButton mFab;

    private BaseRecyclerAdapter mAdapter;
    private RequestsDetailPresenter mPresenter;
    private String mUserId;
    private boolean mIsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        mUserId = extras.getString(EXTRA_USER_ID);
        mIsReady = extras.getBoolean(EXTRA_IS_READY);

        mPresenter = new RequestsDetailPresenter(this, mUserId);

        setupUi();

        mPresenter.getRequestDetail();
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

        mAdapter = new AdminRequestDetailRecyclerAdapter(this);

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

    @Override
    public void updateInfo(List data) {
        mRefreshLayout.setRefreshing(false);

        mAdapter.refresh(data);

        ItemUserCartDetail detail = (ItemUserCartDetail) data.get(0);

        if (detail.getCartItem().isReady()) mFab.setImageResource(R.drawable.ic_uid_white);
        else mFab.setImageResource(R.drawable.ic_done_white);
    }

    public void showError(ErrorType error) {

    }

//    public static void onUidRead(UIDEvent event) {
//        if (event != null)
//            mUserUid = event.getUID();
//    }

    @Subscribe
    public void onUIDEvent(UIDEvent event) {
        if (event != null)
            mUserUid = event.getUID();
    }

    @OnClick(R.id.activity_request_detail_fab)
    void onValidateWithUid() {
        if (!mIsReady) mPresenter.readyUserCart();
        else mPresenter.validateUserCart(mUserUid, false);
    }

    @OnLongClick(R.id.activity_request_detail_fab)
    boolean onForceValidate() {
        mPresenter.validateUserCart(mUserUid, true);
        return true;
    }

    public void showValidationSuccess() {
//        mEventBus.post(new SnackbarEvent(R.string.request_item_validate_success));
    }

    public void showValidationError() {
//        mEventBus.post(new SnackbarEvent(R.string.request_item_validate_error));
    }
}
