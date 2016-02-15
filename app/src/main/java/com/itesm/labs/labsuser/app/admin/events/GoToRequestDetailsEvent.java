package com.itesm.labs.labsuser.app.admin.events;

/**
 * Created by mgradob on 2/15/16.
 */
public class GoToRequestDetailsEvent {

    private String userId;

    public GoToRequestDetailsEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
