package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 2/22/16.
 */
public class ItemClickEvent<T> {

    private T item;

    public ItemClickEvent(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
