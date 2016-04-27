package com.itesm.labs.labsuser.app.commons.contracts;

import java.util.List;

/**
 * Contact used by {@link android.app.Activity}s of {@link android.support.v4.app.Fragment}s which
 * have a {@link android.support.v7.widget.RecyclerView} in its hierarchy.
 * <p>
 * Created by mgradob on 4/23/16.
 */
public interface IListContract {

    /**
     * Sets up the {@link List} of the view.
     */
    void setupList();

    /**
     * Sets up the {@link android.support.v4.widget.SwipeRefreshLayout} of the {@link List}.
     */
    void setupRefresh();

    /**
     * Updates the information of the {@link List}.
     *
     * @param data the list data to update.
     */
    void updateInfo(List data);
}
