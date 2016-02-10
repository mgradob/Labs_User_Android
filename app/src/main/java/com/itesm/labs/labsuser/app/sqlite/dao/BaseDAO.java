package com.itesm.labs.labsuser.app.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.itesm.labs.labsuser.app.sqlite.LabsDatabase;

import javax.inject.Inject;

/**
 * Created by mgradob on 12/6/15.
 */
public abstract class BaseDAO {

    @Inject
    public Context mContext;

    @Inject
    public LabsDatabase mLabsDatabase;

    public SQLiteDatabase mDatabase;

    public BaseDAO() {
        LabsApp.get().inject(this);

        open();
    }

    public void open() {
        mDatabase = mLabsDatabase.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }
}
