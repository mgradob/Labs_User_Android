package com.itesm.labs.labsuser.app.admin.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.views.fragments.RequestsFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/17/16.
 */
public class RequestsPresenter extends BaseFragmentPresenter {

    private static final String TAG = RequestsPresenter.class.getSimpleName();

    @Inject
    CartClient mCartClient;
    @Inject
    UserClient mUserClient;

    private RequestsFragment mView;

    private List<ItemUserCart> usersCartsList = new ArrayList<>();

    public RequestsPresenter(RequestsFragment view) {
        super();
        this.mView = view;
    }

    /**
     * Gets all available requests.
     */
    public void getAllRequests() {
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

                        for (User user : users)
                            if (user.getUserId().equals(userId))
                                builder.setUserName(user.getUserFullName());

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

                        mView.updateInfo(usersCartsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Get carts task error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ItemUserCart> itemUserCarts) {
                        usersCartsList = itemUserCarts;
                    }
                });
    }
}