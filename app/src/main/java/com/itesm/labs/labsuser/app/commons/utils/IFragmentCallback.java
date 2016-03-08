package com.itesm.labs.labsuser.app.commons.utils;

/**
 * Created by mgradob on 3/6/16.
 */
public interface IFragmentCallback<T> {

    /**
     *
     * @param data the data to pass to the child fragment.
     */
    void onListItemClicked(T data);
}
