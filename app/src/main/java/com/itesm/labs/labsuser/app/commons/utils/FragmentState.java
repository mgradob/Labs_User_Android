package com.itesm.labs.labsuser.app.commons.utils;

/**
 * Created by mgradob on 2/15/16.
 */
public enum FragmentState {
    ITEMS_ALL(0),
    ITEMS_DETAILS(1);

    private final int value;

    FragmentState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}