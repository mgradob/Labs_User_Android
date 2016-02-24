package com.itesm.labs.labsuser.app.commons.events;

/**
 * Created by mgradob on 2/21/16.
 */
public class ShowDialogEvent {

    public int titleResource;
    public int contentResource;

    public ShowDialogEvent(int contentResource) {
        this.contentResource = contentResource;
    }

    public ShowDialogEvent(int titleResource, int contentResource) {
        this.titleResource = titleResource;
        this.contentResource = contentResource;
    }
}
