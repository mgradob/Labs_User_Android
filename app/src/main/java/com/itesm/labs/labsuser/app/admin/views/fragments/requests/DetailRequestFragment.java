package com.itesm.labs.labsuser.app.admin.views.fragments.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.RequestDetailRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemRequestDetail;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/12/16.
 */
public class DetailRequestFragment extends LabsBaseFragment {

    private static final String TAG = DetailRequestFragment.class.getSimpleName();

    private static final String KEY_USER_ID = "USER_ID";

    @Bind(R.id.fragment_request_detail_user_info)
    TextView mUserInfo;
    @Bind(R.id.fragment_request_detail_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fragment_request_detail_list)
    RecyclerView mDetailCartList;
    @Bind(R.id.fragment_request_detail_fab)
    FloatingActionButton mDetailFab;

    @Inject
    CartClient mCartClient;
    @Inject
    ComponentClient mComponentClient;
    @Inject
    CategoryClient mCategoryClient;

    private String selectedUserId = "";
    private RequestDetailRecyclerAdapter mAdapter;
    private ArrayList<ItemRequestDetail> detailsList = new ArrayList<>();

    public DetailRequestFragment() {
        // Required empty public constructor
    }

    public static DetailRequestFragment newInstance(String userId) {
        DetailRequestFragment fragment = new DetailRequestFragment();

        Bundle args = new Bundle();
        args.putString(KEY_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedUserId = getArguments().getString(KEY_USER_ID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Validate the request using the user credential.
     */
    @OnClick(R.id.fragment_request_detail_fab)
    void onValidateRequest() {
        // TODO: 2/12/16 validate with UID from user credential.
    }

    /**
     * Force validate the request.
     */
    @OnLongClick(R.id.fragment_request_detail_fab)
    void onForceValidateRequest() {
        // TODO: 2/12/16 validate the requests without the UID from the user credential.
    }

    /**
     * Configures the {@link LinearLayoutManager} and {@link RequestDetailRecyclerAdapter}.
     */
    private void setupDetailRequestList() {
        mDetailCartList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mDetailCartList.hasFixedSize();

        mAdapter = new RequestDetailRecyclerAdapter();

        mDetailCartList.setAdapter(mAdapter);
    }

    /**
     * Configures the {@link SwipeRefreshLayout}.
     */
    private void setupRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.material_red,
                R.color.material_green,
                R.color.material_blue
        );

        mSwipeRefreshLayout.setOnRefreshListener(() -> getRequestDetail(selectedUserId));
    }

    /**
     * Gets a detailed request for a user cart.
     *
     * @param userId the id of the user to query the detail cart from.
     */
    private void getRequestDetail(String userId) {
        mSubscription.unsubscribe();
        mSubscription = mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), userId)
                .flatMap(cartItems -> Observable.from(cartItems))
                .doOnNext(cartItem -> mComponentClient.getComponent(mLabsPreferences.getToken(),
                                mLabsPreferences.getCurrentLab().getLink(), cartItem.getComponentId())
                                .flatMap(component -> mCategoryClient.getCategory(mLabsPreferences.getToken(),
                                                mLabsPreferences.getCurrentLab().getLink(), component.getCategory())
                                                .doOnNext(category -> detailsList.add(new ItemRequestDetail.Builder()
                                                                .setCategoryName(category.getName())
                                                                .setComponentName(component.getName())
                                                                .setCartItem(cartItem)
                                                                .build()
                                                ))
                                )
                )
                .map(cartItem1 -> detailsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemRequestDetail>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get cart details started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get cart details completed");
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Task get cart details error: " + e.getMessage());
                        mSwipeRefreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onNext(ArrayList<ItemRequestDetail> itemRequestDetails) {
                        mAdapter.refresh(itemRequestDetails);
                    }
                });
    }

    /**
     * Updates the API.
     */
    private void validateRequest() {
        // TODO: 2/12/16 validate the request.
    }
}
