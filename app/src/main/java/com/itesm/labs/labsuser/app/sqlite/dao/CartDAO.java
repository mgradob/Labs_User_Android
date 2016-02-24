//package com.itesm.labs.labsuser.app.sqlite.dao;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.util.Log;
//
//import com.mgb.labsapi.models.CartItem;
//import com.mgb.labsapi.models.Component;
//import com.itesm.labs.labsuser.app.sqlite.LabsContract;
//
//import java.util.ArrayList;
//
//import rx.Observable;
//import rx.Subscriber;
//
///**
// * Created by mgradob on 12/21/15.
// */
//public class CartDAO extends BaseDAO {
//
//    private static final String TAG = CartDAO.class.getSimpleName();
//
//    public CartDAO() {
//        super();
//    }
//
//    public CartItem getCartItem(int cartItemId) {
//        Cursor cursor = mDatabase.query(
//                LabsContract.CartTable.TABLE,
//                new String[]{
//                        LabsContract.CartTable.Columns.COLUMN_ID,
//                        LabsContract.CartTable.Columns.COLUMN_STUDENT_ID,
//                        LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID,
//                        LabsContract.CartTable.Columns.COLUMN_QUANTITY,
//                        LabsContract.CartTable.Columns.COLUMN_CHECKOUT,
//                        LabsContract.CartTable.Columns.COLUMN_READY,
//                        LabsContract.CartTable.Columns.COLUMN_DATE_CHECKOUT
//                },
//                LabsContract.CartTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(cartItemId)},
//                null,
//                null,
//                null
//        );
//
//        if (cursor.moveToFirst()) {
//            return new CartItem(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getInt(2),
//                    cursor.getInt(3),
//                    cursor.getInt(4) == 1,
//                    cursor.getInt(5) == 1,
//                    cursor.getString(6)
//            );
//        }
//
//        return null;
//    }
//
//    public ArrayList<CartItem> getCart() {
//        Cursor cursor = mDatabase.query(
//                LabsContract.CartTable.TABLE,
//                new String[]{
//                        LabsContract.CartTable.Columns.COLUMN_ID,
//                        LabsContract.CartTable.Columns.COLUMN_STUDENT_ID,
//                        LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID,
//                        LabsContract.CartTable.Columns.COLUMN_QUANTITY,
//                        LabsContract.CartTable.Columns.COLUMN_CHECKOUT,
//                        LabsContract.CartTable.Columns.COLUMN_READY,
//                        LabsContract.CartTable.Columns.COLUMN_DATE_CHECKOUT
//                },
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        Cursor cursor1 = mDatabase.rawQuery();
//
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//
//        if (cursor.moveToFirst()) {
//            do {
//                CartItem item = new CartItem(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getInt(2),
//                        cursor.getInt(3),
//                        cursor.getInt(4) == 1,
//                        cursor.getInt(5) == 1,
//                        cursor.getString(6)
//                );
//
//                cartItems.add(item);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//
//        return cartItems;
//    }
//
//    public long postCartItem(CartItem cartItem) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.CartTable.Columns.COLUMN_ID, cartItem.getCartId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_STUDENT_ID, cartItem.getStudentId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID, cartItem.getComponentId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_QUANTITY, cartItem.getQuantity());
//        values.put(LabsContract.CartTable.Columns.COLUMN_CHECKOUT, cartItem.isCheckout());
//        values.put(LabsContract.CartTable.Columns.COLUMN_READY, cartItem.isReady());
//        values.put(LabsContract.CartTable.Columns.COLUMN_DATE_CHECKOUT, cartItem.getDateCheckout());
//
//        return mDatabase.insert(LabsContract.CartTable.TABLE, null, values);
//    }
//
//    public long putCartItem(CartItem cartItem) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.CartTable.Columns.COLUMN_ID, cartItem.getCartId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_STUDENT_ID, cartItem.getStudentId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID, cartItem.getComponentId());
//        values.put(LabsContract.CartTable.Columns.COLUMN_QUANTITY, cartItem.getQuantity());
//        values.put(LabsContract.CartTable.Columns.COLUMN_CHECKOUT, cartItem.isCheckout());
//        values.put(LabsContract.CartTable.Columns.COLUMN_READY, cartItem.isReady());
//        values.put(LabsContract.CartTable.Columns.COLUMN_DATE_CHECKOUT, cartItem.getDateCheckout());
//
//        return mDatabase.update(
//                LabsContract.CartTable.TABLE,
//                values,
//                LabsContract.CartTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(cartItem.getCartId())}
//        );
//    }
//
//    public long deleteCartItem(CartItem cartItem) {
//        return mDatabase.delete(
//                LabsContract.ComponentsTable.TABLE,
//                LabsContract.ComponentsTable.Columns.COLUMN_ID + " =?",
//                new String[]{String.valueOf(cartItem.getCartId())}
//        );
//    }
//
//    public Observable<ArrayList<CartItem>> getCartItemFromDb() {
//        return Observable.create(new Observable.OnSubscribe<ArrayList<CartItem>>() {
//            @Override
//            public void call(Subscriber<? super ArrayList<CartItem>> subscriber) {
//                ArrayList<CartItem> cartItems = getCart();
//
//                subscriber.onNext(cartItems);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Void> postCartToDb(final ArrayList<CartItem> cartItems) {
//        return Observable.create(new Observable.OnSubscribe<Void>() {
//            @Override
//            public void call(Subscriber<? super Void> subscriber) {
//                for (CartItem cartItem : cartItems) {
//                    long result = postCartItem(cartItem);
//                    Log.d(TAG, "Post result: " + result);
//                }
//
//                subscriber.onNext(null);
//                subscriber.onCompleted();
//            }
//        });
//    }
//
//    public Observable<Void> addToCartItem(final Component component) {
//        return Observable.create(new Observable.OnSubscribe<Void>() {
//            @Override
//            public void call(Subscriber<? super Void> subscriber) {
//                Cursor cursor = mDatabase.query(
//                        LabsContract.CartTable.TABLE,
//                        new String[]{
//                                LabsContract.CartTable.Columns.COLUMN_ID,
//                                LabsContract.CartTable.Columns.COLUMN_STUDENT_ID,
//                                LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID,
//                                LabsContract.CartTable.Columns.COLUMN_QUANTITY,
//                                LabsContract.CartTable.Columns.COLUMN_CHECKOUT,
//                                LabsContract.CartTable.Columns.COLUMN_READY,
//                                LabsContract.CartTable.Columns.COLUMN_DATE_CHECKOUT
//                        },
//                        LabsContract.CartTable.Columns.COLUMN_COMPONENT_ID + " =?",
//                        new String[]{String.valueOf(component.getId())},
//                        null,
//                        null,
//                        null
//                );
//
//                if (cursor.moveToFirst()) {
//                    CartItem tempItem = new CartItem(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getInt(2),
//                        cursor.getInt(3) + 1,
//                        cursor.getInt(4) == 1,
//                        cursor.getInt(5) == 1,
//                        cursor.getString(6)
//                    );
//
//                    putCartItem(tempItem);
//                } else {
//                    CartItem tempItem = new CartItem(
//                    );
//                }
//            }
//        });
//    }
//}
