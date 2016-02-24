package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 2/23/16.
 */
public class UIDEvent {

    private long UID;

    public UIDEvent(long UID) {
        this.UID = UID;
    }

    public long getUID() {
        return UID;
    }
}
