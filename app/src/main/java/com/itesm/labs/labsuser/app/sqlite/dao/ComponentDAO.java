//package com.itesm.labs.labsuser.app.sqlite.dao;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.util.Log;
//
//import com.mgb.labsapi.models.Component;
//import com.itesm.labs.labsuser.app.sqlite.LabsContract;
//
//import java.util.ArrayList;
//
//import rx.Observable;
//import rx.Subscriber;
//
///**
// * Created by mgradob on 12/6/15.
// */
//public class ComponentDAO extends BaseDAO {
//
//    private static final String TAG = ComponentDAO.class.getSimpleName();
//
//    public ComponentDAO() {
//        super();
//    }
//
//    public Component getComponent(int componentId) {
//        Cursor cursor = mDatabase.query(
//                LabsContract.ComponentsTable.TABLE,
//                new String[]{
//                        LabsContract.ComponentsTable.Columns.COLUMN_ID,
//                        LabsContract.ComponentsTable.Columns.COLUMN_NAME,
//                        LabsContract.ComponentsTable.Columns.COLUMN_NOTE,
//                        LabsContract.ComponentsTable.Columns.COLUMN_TOTAL,
//                        LabsContract.ComponentsTable.Columns.COLUMN_AVAILABLE,
//                        LabsContract.ComponentsTable.Columns.COLUMN_CATEGORY_ID
//                },
//                LabsContract.ComponentsTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(componentId)},
//                null,
//                null,
//                null
//        );
//
//        if (cursor.moveToFirst()) {
//            Component component = new Component();
//            component.setId(cursor.getInt(0));
//            component.setName(cursor.getString(1));
//            component.setNote(cursor.getString(2));
//            component.setTotal(cursor.getInt(3));
//            component.setAvailable(cursor.getInt(4));
//            component.setCategory(cursor.getInt(5));
//
//            cursor.close();
//
//            return component;
//        }
//
//        return null;
//    }
//
//    public ArrayList<Component> getComponents(int categoryId) {
//        Cursor cursor = mDatabase.query(
//                LabsContract.ComponentsTable.TABLE,
//                new String[]{
//                        LabsContract.ComponentsTable.Columns.COLUMN_ID,
//                        LabsContract.ComponentsTable.Columns.COLUMN_NAME,
//                        LabsContract.ComponentsTable.Columns.COLUMN_NOTE,
//                        LabsContract.ComponentsTable.Columns.COLUMN_TOTAL,
//                        LabsContract.ComponentsTable.Columns.COLUMN_AVAILABLE,
//                        LabsContract.ComponentsTable.Columns.COLUMN_CATEGORY_ID
//                },
//                LabsContract.ComponentsTable.Columns.COLUMN_CATEGORY_ID + " =?",
//                new String[]{String.valueOf(categoryId)},
//                null,
//                null,
//                null
//        );
//
//        ArrayList<Component> components = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//            do {
//                Component component = new Component();
//                component.setId(cursor.getInt(0));
//                component.setName(cursor.getString(1));
//                component.setNote(cursor.getString(2));
//                component.setTotal(cursor.getInt(3));
//                component.setAvailable(cursor.getInt(4));
//                component.setCategory(cursor.getInt(5));
//
//                components.add(component);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//
//        return components;
//    }
//
//    public long postComponent(Component component) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_ID, component.getId());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_NAME, component.getName());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_NOTE, component.getNote());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_TOTAL, component.getTotal());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_AVAILABLE, component.getAvailable());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_CATEGORY_ID, component.getCategory());
//
//        return mDatabase.insert(LabsContract.ComponentsTable.TABLE, null, values);
//    }
//
//    public long putComponent(Component component) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_NAME, component.getName());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_NOTE, component.getNote());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_TOTAL, component.getTotal());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_AVAILABLE, component.getAvailable());
//        values.put(LabsContract.ComponentsTable.Columns.COLUMN_CATEGORY_ID, component.getCategory());
//
//        return mDatabase.update(
//                LabsContract.ComponentsTable.TABLE,
//                values,
//                LabsContract.ComponentsTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(component.getId())}
//        );
//    }
//
//    public long deleteComponent(Component component) {
//        return mDatabase.delete(
//                LabsContract.ComponentsTable.TABLE,
//                LabsContract.ComponentsTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(component.getId())}
//        );
//    }
//
//    public Observable<ArrayList<Component>> getComponentsFromDb(final int categoryId) {
//        return Observable.create(new Observable.OnSubscribe<ArrayList<Component>>() {
//            @Override
//            public void call(Subscriber<? super ArrayList<Component>> subscriber) {
//                ArrayList<Component> components = getComponents(categoryId);
//
//                subscriber.onNext(components);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Void> postComponentsToDb(final ArrayList<Component> components) {
//        return Observable.create(new Observable.OnSubscribe<Void>() {
//            @Override
//            public void call(Subscriber<? super Void> subscriber) {
//                for (Component component : components) {
//                    long result = postComponent(component);
//                    Log.d(TAG, "Post result: " + result);
//                }
//
//                subscriber.onNext(null);
//                subscriber.onCompleted();
//            }
//        });
//    }
//}
