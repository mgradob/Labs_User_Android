package com.itesm.labs.labsuser.app.user.adapters.models;

import com.mgb.labsapi.models.Component;

/**
 * Created by mgradob on 12/27/15.
 */
public class ComponentListItem {

    private Component mComponent;
    private int inCart = 0;

    public ComponentListItem() {

    }

    public Component getComponent() {
        return mComponent;
    }

    public void setComponent(Component mComponent) {
        this.mComponent = mComponent;
    }

    public int getInCart() {
        return inCart;
    }

    public void setInCart(int inCart) {
        this.inCart = inCart;
    }

    @Override
    public String toString() {
        return "ComponentListItem{" +
                "mComponent=" + mComponent +
                ", inCart=" + inCart +
                '}';
    }
}
