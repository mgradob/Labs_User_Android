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
public class RequestsFragmentPresenter extends BaseFragmentPresenter {

    private static final String TAG = RequestsFragmentPresenter.class.getSimpleName();

    @Inject
    CartClient mCartClient;
    @Inject
    UserClient mUserClient;

    private RequestsFragment mView;

    private String selectedUserId;

    private User mValidateUser;

    private List<ItemUserCart> userCartsList = new ArrayList<>();
    private List<CartItem> cartItemsList = new ArrayList<>();

    public RequestsFragmentPresenter(RequestsFragment view) {
        super();
        this.mView = view;
    }

    //region UI
    public void setSelectedUserId(String selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
    //endregion

    //region API calls.

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

                        mView.updateAll(userCartsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Get carts task error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<ItemUserCart> itemUserCarts) {
                        userCartsList = itemUserCarts;
                    }
                });
    }

    /**
     * Gets the details of a user request.
     */
    public void getRequestDetail() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mCartClient.getCartItemsOf(mLabsPreferences.getToken(),
                        mLabsPreferences.getCurrentLab().getLink(), selectedUserId),
                mUserClient.getUser(mLabsPreferences.getToken(), selectedUserId),
                (cartItems, user) -> {
                    mValidateUser = user;
                    return cartItems;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Get carts task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Get carts task finished");

                        mView.updateDetails(cartItemsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Get carts task error: " + e.getMessage());

                        mView.showError();
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> cartItems) {
                        cartItemsList = cartItems;
                    }
                });
    }

    /**
     * Updates the user cart (validates it).
     */
    public void updateUserCart(long UID, boolean force) {
        if (!force) {
            if (UID != mValidateUser.getUserUid()) mView.showError();
        }

        mSubscription.unsubscribe();
        mSubscription = Observable.from(cartItemsList)
                .doOnNext(cartItem -> mCartClient.editCartItem(mLabsPreferences.getToken(),
                                mLabsPreferences.getCurrentLab().getLink(), cartItem.getCartId(), cartItem)
                )
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task validate cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task validate cart completed");

                        mView.showValidationSuccess();
                        mView.updateDetails(cartItemsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task validate cart error: " + e.getMessage());

                        mView.showValidationError();
                    }

                    @Override
                    public void onNext(List<CartItem> cartItems) {
                        cartItemsList = cartItems;
                    }
                });
    }
    //endregion
}