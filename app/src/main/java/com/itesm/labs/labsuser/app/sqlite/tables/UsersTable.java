package com.itesm.labs.labsuser.app.sqlite.tables;

import android.net.Uri;

import com.itesm.labs.labsuser.app.sqlite.LabsContract;
import com.itesm.labs.labsuser.app.sqlite.columns.BaseColumns;
import com.itesm.labs.labsuser.app.sqlite.columns.UsersColumns;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.History;
import com.mgb.labsapi.models.Laboratory;

/**
 * Created by mgradob on 1/26/16.
 */
public class UsersTable implements LabsContract, BaseColumns, UsersColumns {

    /**
     * Content URI for the users table.
     */
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_USERS)
            .build();

    /**
     * MIME type for cursor with 'n' items.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.itesm.labs.user";

    /**
     * MIME type for cursor with one item.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.itesm.labs.user";

    //region Sorts

    /**
     * Default "ORDER BY" clause.
     */
    public static final String DEFAULT_SORT = UsersColumns.ID_CREDENTIAL + " ASC";
    //endregion

    //region Helper methods

    /**
     * Builds {@link Uri} for the requested {@link UsersTable#ID_STUDENT}.
     */
    public static Uri buildUserUri(String userId) {
        return CONTENT_URI.buildUpon()
                .appendPath(userId)
                .build();
    }

    /**
     * Read {@link UsersTable#ID_STUDENT} from {@link UsersTable} {@link Uri}.
     */
    public static String getUserId(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    /**
     * Builds {@link Uri} that references any {@link Laboratory}
     * with the {@link UsersTable#ID_STUDENT}
     */
    public static Uri buildLabsDirUri(String userId) {
        return CONTENT_URI.buildUpon()
                .appendPath(userId)
                .appendPath(PATH_LABS)
                .build();
    }

    /**
     * Builds {@link Uri} that references any {@link CartItem}
     * with the {@link UsersTable#ID_STUDENT}
     */
    public static Uri buildCartDirUri(String userId) {
        return CONTENT_URI.buildUpon()
                .appendPath(userId)
                .appendPath(PATH_CART)
                .build();
    }

    /**
     * Builds {@link Uri} that references any {@link History}
     * with the {@link UsersTable#ID_STUDENT}
     */
    public static Uri buildHistoriesDirUri(String userId) {
        return CONTENT_URI.buildUpon()
                .appendPath(userId)
                .appendPath(PATH_HISTORIES)
                .build();
    }
    //endregion
}
