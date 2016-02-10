package com.itesm.labs.labsuser.app.sqlite;

import android.net.Uri;

/**
 * Created by mgradob on 12/6/15.
 */
public interface LabsContract {

    /**
     * Base content authority string.
     */
    String CONTENT_AUTHORITY = "com.itesm.labs";

    /**
     * Base content URI for the database.
     */
    Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //region Paths
    String PATH_USERS = "users";
    String PATH_LABS = "labs";
    String PATH_CATEGORIES = "categories";
    String PATH_COMPONENTS = "components";
    String PATH_CART = "cart";
    String PATH_HISTORIES = "histories";
    //endregion
}
