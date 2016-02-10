package com.itesm.labs.labsuser.app.sqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.mgb.labsapi.models.Category;
import com.itesm.labs.labsuser.app.sqlite.LabsContract;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 12/6/15.
 */
public class CategoriesDAO extends BaseDAO {

    private static final String TAG = CategoriesDAO.class.getSimpleName();

    public CategoriesDAO() {
        super();
    }

    public Category getCategory(int categoryId) {
        Cursor cursor = mDatabase.query(
                LabsContract.CategoriesTable.TABLE,
                new String[]{
                        LabsContract.CategoriesTable.Columns.COLUMN_ID,
                        LabsContract.CategoriesTable.Columns.COLUMN_NAME,
                        LabsContract.CategoriesTable.Columns.COLUMN_IMAGE
                },
                LabsContract.CategoriesTable.Columns.COLUMN_ID + " =?",
                new String[]{String.valueOf(categoryId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Category category = new Category();
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            category.setImageResource(cursor.getInt(2));

            cursor.close();

            return category;
        } else return null;
    }

    public ArrayList<Category> getCategories() {
        Cursor cursor = mDatabase.query(
                LabsContract.CategoriesTable.TABLE,
                new String[]{
                        LabsContract.CategoriesTable.Columns.COLUMN_ID,
                        LabsContract.CategoriesTable.Columns.COLUMN_NAME,
                        LabsContract.CategoriesTable.Columns.COLUMN_IMAGE
                },
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Category> categories = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setImageResource(cursor.getInt(2));

                categories.add(category);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return categories;
    }

    public long postCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(LabsContract.CategoriesTable.Columns.COLUMN_ID, category.getId());
        values.put(LabsContract.CategoriesTable.Columns.COLUMN_NAME, category.getName());
        values.put(LabsContract.CategoriesTable.Columns.COLUMN_IMAGE, category.getImageResource());

        return mDatabase.insert(LabsContract.CategoriesTable.TABLE, null, values);
    }

    public long putCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(LabsContract.CategoriesTable.Columns.COLUMN_NAME, category.getName());
        values.put(LabsContract.CategoriesTable.Columns.COLUMN_IMAGE, category.getImageResource());

        return mDatabase.update(
                LabsContract.CategoriesTable.TABLE,
                values,
                LabsContract.CategoriesTable.Columns.COLUMN_ID + " =?",
                new String[]{String.valueOf(category.getId())}
        );
    }

    public long deleteCategory(Category category) {
        return mDatabase.delete(
                LabsContract.CategoriesTable.TABLE,
                LabsContract.CategoriesTable.Columns.COLUMN_ID + " =?",
                new String[]{String.valueOf(category.getId())}
        );
    }

    public Observable<ArrayList<Category>> getCategoriesFromDb() {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Category>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Category>> subscriber) {
                ArrayList<Category> categories = getCategories();

                subscriber.onNext(categories);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> postCategoriesToDb(final ArrayList<Category> categories) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                for (Category category : categories) {
                    long result = postCategory(category);
                    Log.d(TAG, "Post result: " + result);
                }

                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }
}
