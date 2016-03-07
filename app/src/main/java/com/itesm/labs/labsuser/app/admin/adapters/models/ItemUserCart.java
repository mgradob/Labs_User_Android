package com.itesm.labs.labsuser.app.admin.adapters.models;

import com.mgb.labsapi.models.CartItem;

import java.util.ArrayList;

/**
 * Created by mgradob on 4/6/15.
 */
public class ItemUserCart {

    private String userName;
    private String userId;
    private String cartDate;
    private ArrayList<CartItem> cartList;
    private boolean ready = false;

    public ItemUserCart(Builder builder) {
        this.userName = builder.userName;
        this.userId = builder.userId;
        this.cartDate = builder.cartDate;
        this.cartList = builder.cartList;
        this.ready = builder.ready;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getCartDate() {
        return cartDate;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public String toString() {
        return "CartListItem{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", cartDate='" + cartDate + '\'' +
                ", cartList=" + cartList +
                ", ready=" + ready +
                '}';
    }

    public static class Builder {
        private String userName;
        private String userId;
        private String cartDate;
        private ArrayList<CartItem> cartList;
        private boolean ready;

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setCartDate(String cartDate) {
            this.cartDate = cartDate;
            return this;
        }

        public Builder setCartList(ArrayList<CartItem> cartList) {
            this.cartList = cartList;
            return this;
        }

        public Builder setReady(boolean ready) {
            this.ready = ready;
            return this;
        }

        public ItemUserCart build() {
            return new ItemUserCart(this);
        }
    }
}
