package com.itesm.labs.labsuser.app.admin.views.fragments.requests;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.RequestRecyclerAdapter;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.events.GoToRequestDetailsEvent;
import com.itesm.labs.labsuser.app.bases.LabsBaseFragment;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/12/16.
 */
public class AllRequestsFragment extends LabsBaseFragment {

    private static final String TAG = AllRequestsFragment.class.getSimpleName();

    @Bind(R.id.fragment_requests_all_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fragment_requests_all_recycler_view)
    RecyclerView mCartsListView;

    @Inject
    CartClient mCartClient;
    @Inject
    UserClient mUserClient;

    private RequestRecyclerAdapter mAdapter;
    private ArrayList<ItemUserCart> cartsList = new ArrayList<>();

    public AllRequestsFragment() {
        // Required empty public constructor
    }

    //region Stock
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRequestsList();
        setupRefreshLayout();

        getAllRequests();
    }
    //endregion

    @OnItemClick(R.id.fragment_requests_all_recycler_view)
    void onCartClicked(int position) {
        mEventBus.post(new GoToRequestDetailsEvent(cartsList.get(position).getUserId()));
    }

    //region Fragment Setup
    /**
     * Configures the {@link LinearLayoutManager} and {@link RequestRecyclerAdapter}.
     */
    private void setupRequestsList() {
        mCartsListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mCartsListView.hasFixedSize();

        mAdapter = new RequestRecyclerAdapter();

        mCartsListView.setAdapter(mAdapter);
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

        mSwipeRefreshLayout.setOnRefreshListener(() -> getAllRequests());
    }

    /**
     * Gets all available requests on the API.
     */
    private void getAllRequests() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mCartClient.getCartsItems(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink()),
                mUserClient.getUsers(mLabsPreferences.getToken()),
                (cartItems, users) -> {
                    ArrayList<String> usersList = new ArrayList<>();
                    ArrayList<ItemUserCart> carts = new ArrayList<>();

                    for (CartItem item : cartItems)
                        if (!usersList.contains(item.getStudentId()))
                            usersList.add(item.getStudentId());

                    for (String userId : usersList) {
                        ArrayList<CartItem> userItems = new ArrayList<>();
                        ItemUserCart.Builder builder = new ItemUserCart.Builder();
                        builder.setUserId(userId);

                        for (User user : users) builder.setUserName(user.getUserFullName());

                        for (int i = 0; i < cartItems.size(); i++)
                            if (cartItems.get(i).getStudentId().equals(userId))
                                userItems.add(cartItems.remove(i));

                        builder.setCartList(userItems);
                        builder.setCartDate(userItems.get(0).getDateCheckout());
                        builder.setReady(userItems.get(0).isReady());

                        carts.add(builder.build());
                    }

                    return carts;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemUserCart>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Get carts task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Get carts task finished");
                        mSwipeRefreshLayout.setRefreshing(false);

                        mAdapter.refresh(cartsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Get carts task error: " + e.getMessage());

                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ArrayList<ItemUserCart> itemUserCarts) {
                        cartsList = itemUserCarts;
                    }
                });
    }
    //endregion
}
