package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 2/22/16.
 */
public class ItemLongClickEvent<T> {

    private T item;

    public ItemLongClickEvent(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}
