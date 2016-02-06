package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 4/6/15.
 */
public class CartItem {

    @SerializedName("id_cart")
    private int cartId;
    @SerializedName("id_student_fk")
    private String studentId;
    @SerializedName("id_component_fk")
    private int componentId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("checkout")
    private boolean checkout;
    @SerializedName("ready")
    private boolean ready;
    @SerializedName("date_checkout")
    private String dateCheckout;

    public CartItem(Builder builder) {
        this.cartId = builder.cartId;
        this.studentId = builder.studentId;
        this.componentId = builder.componentId;
        this.quantity = builder.quantity;
        this.checkout = builder.checkout;
        this.ready = builder.ready;
        this.dateCheckout = builder.dateCheckout;
    }

    public int getCartId() {
        return cartId;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getComponentId() {
        return componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isCheckout() {
        return checkout;
    }

    public boolean isReady() {
        return ready;
    }

    public String getDateCheckout() {
        return dateCheckout;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", studentId='" + studentId + '\'' +
                ", componentId=" + componentId +
                ", quantity=" + quantity +
                ", checkout=" + checkout +
                ", ready=" + ready +
                ", dateCheckout='" + dateCheckout + '\'' +
                '}';
    }

    public static class Builder {
        private int cartId;
        private String studentId;
        private int componentId;
        private int quantity;
        private boolean checkout;
        private boolean ready;
        private String dateCheckout;

        public Builder setCartId(int cartId) {
            this.cartId = cartId;
            return this;
        }

        public Builder setStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder setComponentId(int componentId) {
            this.componentId = componentId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setCheckout(boolean checkout) {
            this.checkout = checkout;
            return this;
        }

        public Builder setReady(boolean ready) {
            this.ready = ready;
            return this;
        }

        public Builder setDateCheckout(String dateCheckout) {
            this.dateCheckout = dateCheckout;
            return this;
        }

        public CartItem build() {
            return new CartItem(this);
        }
    }
}
