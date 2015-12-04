package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.app.application.AppGlobals;
import com.itesm.labs.labsuser.app.rest.models.CartItem;
import com.itesm.labs.labsuser.app.rest.models.Component;
import com.itesm.labs.labsuser.app.rest.services.CartService;
import com.itesm.labs.labsuser.app.rest.services.ComponentService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/31/15.
 */
public class CartClient {

    private ComponentService mComponentService;

    private CartService mCartService;

    public CartClient(CartService mCartService, ComponentService mComponentService) {
        this.mCartService = mCartService;
        this.mComponentService = mComponentService;
    }

    public Observable<ArrayList<CartItem>> getCartItemsOf(final String token, final String lab, final String userId) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<CartItem>>() {
            @Override
            public void call(Subscriber<? super ArrayList<CartItem>> subscriber) {
                ArrayList<CartItem> cartItems = mCartService.getCartItemsOf(token, lab, userId);

                for (CartItem item : cartItems) {
                    Component tempComponent = mComponentService.getComponent(token, lab, item.getComponentId());
                    item.setComponentName(tempComponent.getName());
                    item.setComponentNote(tempComponent.getNote());
                }

                subscriber.onNext(cartItems);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postNewCartItem(final String token, final String lab, final CartItem item) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCartService.postNewCartItem(token, lab, item);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postNewCart(final String token, final String lab, final ArrayList<CartItem> cart, final AppGlobals mAppGlobals) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (CartItem cartItem : cart) {
                    CartItem newItem = new CartItem(
                            cartItem.getCartId(),
                            mAppGlobals.getUser().getUserId(),
                            cartItem.getComponentId(),
                            cartItem.getQuantity(),
                            cartItem.isCheckout(),
                            cartItem.isReady(),
                            cartItem.getDateCheckout());
                    mCartService.postNewCartItem(token, lab, newItem);
                }

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> editCartItem(final String token, final String lab, final int cartId, final CartItem cartItemBody) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                mCartService.editCartItem(token, lab, cartId, cartItemBody);

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> deleteCart(final String token, final String lab, final String userId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                ArrayList<CartItem> cart = mCartService.getCartItemsOf(token, lab, userId);

                for (CartItem cartItem : cart)
                    mCartService.deleteCartItem(token, lab, cartItem.getCartId());

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
