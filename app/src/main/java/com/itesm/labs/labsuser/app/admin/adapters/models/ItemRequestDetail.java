package com.itesm.labs.labsuser.app.admin.adapters.models;

import com.mgb.labsapi.BuildConfig;
import com.mgb.labsapi.models.CartItem;

/**
 * Created by mgradob on 1/1/16.
 */
public class ItemRequestDetail {

    private String categoryName;
    private String componentName;
    private CartItem cartItem;

    public ItemRequestDetail() {
    }

    public ItemRequestDetail(Builder builder) {
        this.categoryName = builder.categoryName;
        this.componentName = builder.componentName;
        this.cartItem = builder.cartItem;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public static class Builder {
        private String categoryName;
        private String componentName;
        private CartItem cartItem;

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder setComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public Builder setCartItem(CartItem cartItem) {
            this.cartItem = cartItem;
            return this;
        }

        public ItemRequestDetail build() {
            return new ItemRequestDetail(this);
        }
    }
}
