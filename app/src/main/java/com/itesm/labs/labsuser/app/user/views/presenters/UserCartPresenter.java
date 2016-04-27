package com.itesm.labs.labsuser.app.user.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.user.views.fragments.UserCartFragment;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 4/25/16.
 */
public class UserCartPresenter extends BaseFragmentPresenter {

    public static final String TAG = UserCartPresenter.class.getSimpleName();

    UserCartFragment mView;

    @Inject
    CartClient mCartClient;
    @Inject
    ComponentClient mComponentClient;
    @Inject
    CategoryClient mCategoryClient;

    ArrayList<CartItem> mCart = new ArrayList<>();

    public UserCartPresenter(UserCartFragment mView) {
        super();
        this.mView = mView;
    }

    public void getCart() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mLabsPreferences.getUserId()),
                mComponentClient.getAllComponents(mLabsPreferences.getToken(), mLabsPreferences.getLabLink()),
                mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getLabLink()),
                (cartItems, components, categories) -> {
                    ArrayList<CartItem> cart = new ArrayList<>();

                    for (CartItem cartItem : cartItems) {
                        for (Component component : components) {
                            if (component.getId() == cartItem.getComponentId()) {
                                cartItem.setComponentName(component.getName());
                                cartItem.setComponentNote(component.getNote());

                                for (Category category : categories) {
                                    if (category.getId() == component.getCategory())
                                        cartItem.setCategoryName(category.getName());
                                }
                            }
                        }

                        cart.add(cartItem);
                    }

                    return cart;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get cart completed");

                        mView.updateInfo(mCart);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get cart error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> userCartItems) {
                        mCart = userCartItems;
                    }
                });
    }

    public void postUserCart() {
        mSubscription.unsubscribe();
        mSubscription = Observable.from(mCart)
                .map(userCartItem -> {
                    mCartClient.deleteCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), userCartItem.getCartId());
                    return userCartItem;
                })
                .map(userCartItem1 -> {
                    userCartItem1.setReady(false);
                    userCartItem1.setCheckout(false);
                    mCartClient.postNewCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), userCartItem1);
                    return userCartItem1;
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Post task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Post task finished");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Post task error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<CartItem> userCartItems) {

                    }
                });
    }
}
