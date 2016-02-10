package com.itesm.labs.labsuser.app.application;

/**
 * Created by mgradob on 12/27/15.
 */
public enum AppFlow {
    USER_FLOW(1),
    ADMIN_FLOW(2),
    LOGOUT_FLOW(0);

    private final int value;

    AppFlow(int i) {
        this.value = i;
    }

    public static AppFlow getValue(int value) {
        switch (value) {
            case 0:
                return LOGOUT_FLOW;
            case 1:
                return USER_FLOW;
            case 2:
                return ADMIN_FLOW;
            default:
                return LOGOUT_FLOW;
        }
    }

    public int getValue() {
        return value;
    }
}
