package com.itesm.labs.labsuser.app.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mgradob on 6/8/15.
 */
public class LabsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "labs.db";
    private static final int DATABASE_VERSION = 1;

    public LabsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LabsContract.UsersTable.Queries.CREATE_TABLE);
        db.execSQL(LabsContract.LabsTable.Queries.CREATE_TABLE);
        db.execSQL(LabsContract.CategoriesTable.Queries.CREATE_TABLE);
        db.execSQL(LabsContract.ComponentsTable.Queries.CREATE_TABLE);
        db.execSQL(LabsContract.HistoriesTable.Queries.CREATE_TABLE);
        db.execSQL(LabsContract.CartTable.Queries.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LabsContract.UsersTable.Queries.DELETE_TABLE);
        db.execSQL(LabsContract.LabsTable.Queries.DELETE_TABLE);
        db.execSQL(LabsContract.CategoriesTable.Queries.DELETE_TABLE);
        db.execSQL(LabsContract.ComponentsTable.Queries.DELETE_TABLE);
        db.execSQL(LabsContract.HistoriesTable.Queries.DELETE_TABLE);
        db.execSQL(LabsContract.CartTable.Queries.DELETE_TABLE);

        onCreate(db);
    }
}
