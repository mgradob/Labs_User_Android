package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 3/14/16.
 */
public class SnackbarEvent {

    private int bodyRes;

    public SnackbarEvent(int bodyRes) {
        this.bodyRes = bodyRes;
    }

    public int getBodyRes() {
        return bodyRes;

    }
}
