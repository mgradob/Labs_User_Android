package com.itesm.labs.labsuser.app.sqlite.tables;

import android.net.Uri;

import com.itesm.labs.labsuser.app.sqlite.LabsContract;
import com.itesm.labs.labsuser.app.sqlite.columns.BaseColumns;
import com.itesm.labs.labsuser.app.sqlite.columns.CategoriesColumns;

/**
 * Created by mgradob on 1/26/16.
 */
public class ComponentsTable implements LabsContract, BaseColumns, CategoriesColumns {

    /**
     * Content URI for the components table.
     */
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_COMPONENTS)
            .build();

    /**
     * MIME type for cursor with 'n' items.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.itesm.labs.component";

    /**
     * MIME type for cursor with one item.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.itesm.labs.component";

    //region Sorts
    /**
     * Default "ORDER BY" clause.
     */
    public static final String DEFAULT_SORT = BaseColumns._ID + " ASC";
    //endregion

    //region Helper methods

    /**
     * Builds {@link Uri} for the requested {@link LabsTable#NAME}.
     */
    public static Uri buildLabUri(String userId) {
        return CONTENT_URI.buildUpon()
                .appendPath(userId)
                .build();
    }

    /**
     * Read {@link LabsTable#NAME} from {@link LabsTable} {@link Uri}.
     */
    public static String getLabName(Uri uri) {
        return uri.getPathSegments().get(1);
    }
    //endregion
}
