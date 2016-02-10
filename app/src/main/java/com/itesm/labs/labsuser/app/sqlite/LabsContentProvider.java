package com.itesm.labs.labsuser.app.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.itesm.labs.labsuser.app.application.LabsApp;

import javax.inject.Inject;

/**
 * Created by mgradob on 12/20/15.
 */
public class LabsContentProvider extends ContentProvider {

    private static final int CATEGORIES = 101;
    private static final int CATEGORIES_ID = 102;
    private static final int COMPONENTS = 103;
    private static final int COMPONENTS_ID = 104;

    final UriMatcher mUriMatcher = buildUriMatcher();

    @Inject
    Context mContext;
    @Inject
    LabsDatabase mLabsDatabase;

    private UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(LabsContract.AUTHORITY, LabsContract.CategoriesTable.TABLE, CATEGORIES);
        matcher.addURI(LabsContract.AUTHORITY, LabsContract.CategoriesTable.TABLE + "/#", CATEGORIES_ID);
        matcher.addURI(LabsContract.AUTHORITY, LabsContract.ComponentsTable.TABLE, COMPONENTS);
        matcher.addURI(LabsContract.AUTHORITY, LabsContract.ComponentsTable.TABLE + "/#", COMPONENTS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        LabsApp.get().inject(this);

        return mLabsDatabase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (mUriMatcher.match(uri)) {
            case CATEGORIES_ID:
                queryBuilder.appendWhere(LabsContract.CategoriesTable.Columns.COLUMN_ID + "=" + uri.getLastPathSegment());
            case CATEGORIES:
                queryBuilder.setTables(LabsContract.CategoriesTable.TABLE);
                break;
            case COMPONENTS_ID:
                queryBuilder.appendWhere(LabsContract.ComponentsTable.Columns.COLUMN_ID + "=" + uri.getLastPathSegment());
            case COMPONENTS:
                queryBuilder.setTables(LabsContract.ComponentsTable.TABLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(mLabsDatabase.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(mContext.getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
