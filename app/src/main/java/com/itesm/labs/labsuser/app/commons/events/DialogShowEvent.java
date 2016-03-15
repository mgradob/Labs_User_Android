package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 2/21/16.
 */
public class DialogShowEvent {

    public int titleResource;
    public int contentResource;

    public DialogShowEvent(int contentResource) {
        this.contentResource = contentResource;
    }

    public DialogShowEvent(int titleResource, int contentResource) {
        this.titleResource = titleResource;
        this.contentResource = contentResource;
    }
}
