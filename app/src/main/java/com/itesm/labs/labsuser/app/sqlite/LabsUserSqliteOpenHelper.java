//package com.itesm.labs.labsuser.app.sqlite;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.mgb.labsapi.models.CartItem;
//import com.mgb.labsapi.models.Component;
//import com.mgb.labsapi.models.User;
//
//import java.util.ArrayList;
//
///**
// * Created by mgradob on 6/8/15.
// */
//public class LabsUserSqliteOpenHelper extends SQLiteOpenHelper {
//
//    //region CART_ITEM Table.
//    public static final String CART_ITEM_TABLE = "CART_ITEM";
//    // Columnas.
//    public static final String CART_ITEM_COLUMN_ID = "CART_ITEM_COLUMN_ID";
//    public static final String CART_ITEM_COLUMN_USER_ID = "CART_ITEM_COLUMN_USER_ID";
//    public static final String CART_ITEM_COLUMN_COMPONENT_ID = "CART_ITEM_COLUMN_COMPONENT_ID";
//    public static final String CART_ITEM_COLUMN_QUANTITY = "CART_ITEM_COLUMN_QUANTITY";
//    public static final String CART_ITEM_COLUMN_CHECKOUT = "CART_ITEM_COLUMN_CHECKOUT";
//    public static final String CART_ITEM_COLUMN_READY = "CART_ITEM_COLUMN_READY";
//    public static final String CART_ITEM_COLUMN_DATE_CHECKOUT = "CART_ITEM_COLUMN_DATE_CHECKOUT";
//    public static final String CART_ITEM_TABLE_CREATE = "CREATE TABLE " + CART_ITEM_TABLE +
//            "(" +
//            CART_ITEM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//            CART_ITEM_COLUMN_COMPONENT_ID + " INTEGER, " +
//            CART_ITEM_COLUMN_QUANTITY + " INTEGER " +
//            ")";
//    //region COMPONENT Table.
//    public static final String COMPONENT_TABLE = "COMPONENT";
//    public static final String COMPONENT_COLUMN_COMPONENT_ID = "COMPONENT_COLUMN_COMPONENT_ID";
//    //endregion
//    public static final String COMPONENT_COLUMN_CATEGORY_ID = "COMPONENT_COLUMN_CATEGORY_ID";
//    public static final String COMPONENT_COLUMN_NAME = "COMPONENT_COLUMN_NAME";
//    public static final String COMPONENT_COLUMN_NOTE = "COMPONENT_COLUMN_NOTE";
//    public static final String COMPONENT_COLUMN_TOTAL = "COMPONENT_COLUMN_TOTAL";
//    public static final String COMPONENT_COLUMN_AVAILABLE = "COMPONENT_COLUMN_AVAILABLE";
//    public static final String COMPONENT_TABLE_CREATE = "CREATE TABLE " + COMPONENT_TABLE +
//            "(" +
//            COMPONENT_COLUMN_COMPONENT_ID + " INTEGER PRIMARY KEY, " +
//            COMPONENT_COLUMN_CATEGORY_ID + " INTEGER, " +
//            COMPONENT_COLUMN_NAME + " TEXT, " +
//            COMPONENT_COLUMN_NOTE + " TEXT, " +
//            COMPONENT_COLUMN_TOTAL + " INTEGER, " +
//            COMPONENT_COLUMN_AVAILABLE + " INTEGER" +
//            ")";
//    private static final String DATABASE_NAME = "LabsUser.db";
//    private static final int DATABASE_VERSION = 1;
//    //endregion
//
//    public LabsUserSqliteOpenHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CART_ITEM_TABLE_CREATE);
//        db.execSQL(COMPONENT_TABLE_CREATE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + CART_ITEM_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + COMPONENT_TABLE);
//        onCreate(db);
//    }
//
//    //region CartItem methods.
//
//    /**
//     * Add a new cart item to the table.
//     *
//     * @param item the item to add.
//     */
//    public void addNewCartItem(CartItem item) {
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(CART_ITEM_COLUMN_USER_ID, item.getStudentId());
//        values.put(CART_ITEM_COLUMN_COMPONENT_ID, item.getComponentId());
//        values.put(CART_ITEM_COLUMN_QUANTITY, item.getQuantity());
//        values.put(CART_ITEM_COLUMN_CHECKOUT, item.isCheckout());
//        values.put(CART_ITEM_COLUMN_READY, item.isReady());
//        values.put(CART_ITEM_COLUMN_DATE_CHECKOUT, item.getDateCheckout());
//
//        database.insert(CART_ITEM_TABLE, null, values);
//        database.close();
//    }
//
//    /**
//     * Obtain a cart for a user of all requests.
//     *
//     * @param user the user to get the cart for.
//     * @return the filtered cart of the user.
//     */
//    public UserCart getCartOf(User user) {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
//                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
//                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
//                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
//                new String[]{user.getUserId()}, null, null, null, null);
//
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                CartItem item = new CartItem();
//                item.setStudentId(cursor.getString(0));
//                item.setComponentId(cursor.getInt(1));
//                item.setQuantity(cursor.getInt(2));
//                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
//                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
//                item.setDateCheckout(cursor.getString(5));
//
//                cartItems.add(item);
//            } while (cursor.moveToNext());
//        }
//
//        return new UserCart(user.getFullName(), user.getUserId(),
//                cartItems.get(cartItems.size() - 1).getDateCheckout(), cartItems,
//                cartItems.get(cartItems.size() - 1).isReady());
//    }
//
//    /**
//     * Get the list of all items in an user cart.
//     *
//     * @param user the user to get the items for.
//     * @return the list of items of the user.
//     */
//    public ArrayList<CartItem> getItemsOf(User user) {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
//                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
//                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
//                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
//                new String[]{user.getUserId()}, null, null, null, null);
//
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                CartItem item = new CartItem();
//                item.setStudentId(cursor.getString(0));
//                item.setComponentId(cursor.getInt(1));
//                item.setQuantity(cursor.getInt(2));
//                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
//                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
//                item.setDateCheckout(cursor.getString(5));
//
//                cartItems.add(item);
//            } while (cursor.moveToNext());
//        }
//
//        return cartItems;
//    }
//
//    /**
//     * Get the list of all items in an user cart.
//     *
//     * @param userId the id of the user to get the items for.
//     * @return the list of items of the user.
//     */
//    public ArrayList<CartItem> getItemsOf(String userId) {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = database.query(CART_ITEM_TABLE, new String[]{CART_ITEM_COLUMN_USER_ID,
//                        CART_ITEM_COLUMN_COMPONENT_ID, CART_ITEM_COLUMN_QUANTITY,
//                        CART_ITEM_COLUMN_CHECKOUT, CART_ITEM_COLUMN_READY,
//                        CART_ITEM_COLUMN_DATE_CHECKOUT}, CART_ITEM_COLUMN_USER_ID + "=?",
//                new String[]{userId}, null, null, null, null);
//
//        ArrayList<CartItem> cartItems = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                CartItem item = new CartItem();
//                item.setStudentId(cursor.getString(0));
//                item.setComponentId(cursor.getInt(1));
//                item.setQuantity(cursor.getInt(2));
//                item.setCheckout(Boolean.parseBoolean(cursor.getString(3)));
//                item.setReady(Boolean.parseBoolean(cursor.getString(4)));
//                item.setDateCheckout(cursor.getString(5));
//
//                cartItems.add(item);
//            } while (cursor.moveToNext());
//        }
//
//        return cartItems;
//    }
//    //endregion
//
//    //region Component methods.
//
//    /**
//     * Adds a component to the table.
//     *
//     * @param component the component to be added.
//     */
//    public void addComponent(Component component) {
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COMPONENT_COLUMN_COMPONENT_ID, component.getId());
//        values.put(COMPONENT_COLUMN_CATEGORY_ID, component.getCategory());
//        values.put(COMPONENT_COLUMN_NAME, component.getName());
//        values.put(COMPONENT_COLUMN_NOTE, component.getNote());
//        values.put(COMPONENT_COLUMN_TOTAL, component.getTotal());
//        values.put(COMPONENT_COLUMN_AVAILABLE, component.getAvailable());
//
//        database.insert(COMPONENT_TABLE, null, values);
//        database.close();
//    }
//
//    /**
//     * Updates a component of the table.
//     *
//     * @param component the component to be updated.
//     */
//    public void updateComponent(Component component) {
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COMPONENT_COLUMN_COMPONENT_ID, component.getId());
//        values.put(COMPONENT_COLUMN_CATEGORY_ID, component.getCategory());
//        values.put(COMPONENT_COLUMN_NAME, component.getName());
//        values.put(COMPONENT_COLUMN_NOTE, component.getNote());
//        values.put(COMPONENT_COLUMN_TOTAL, component.getTotal());
//        values.put(COMPONENT_COLUMN_AVAILABLE, component.getAvailable());
//
//        database.update(COMPONENT_TABLE, values, COMPONENT_COLUMN_COMPONENT_ID + "=?",
//                new String[]{"" + component.getId()});
//        database.close();
//    }
//
//    /**
//     * Deletes a component of the table.
//     *
//     * @param component the component to be deleted.
//     */
//    public void deleteComponent(Component component) {
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        database.delete(COMPONENT_TABLE, COMPONENT_COLUMN_COMPONENT_ID + "=?",
//                new String[]{"" + component.getId()});
//        database.close();
//    }
//
//    public Component getComponent(String componentId) {
//        SQLiteDatabase database = this.getReadableDatabase();
//
//        Cursor cursor = database.query(COMPONENT_TABLE, new String[]{COMPONENT_COLUMN_COMPONENT_ID,
//                        COMPONENT_COLUMN_CATEGORY_ID, COMPONENT_COLUMN_NAME, COMPONENT_COLUMN_NOTE,
//                        COMPONENT_COLUMN_TOTAL, COMPONENT_COLUMN_AVAILABLE},
//                COMPONENT_COLUMN_COMPONENT_ID + "=?", new String[]{componentId}, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Component component = new Component();
//        component.setId(cursor.getInt(0));
//        component.setCategory(cursor.getInt(1));
//        component.setName(cursor.getString(2));
//        component.setNote(cursor.getString(3));
//        component.setTotal(cursor.getInt(4));
//        component.setAvailable(cursor.getInt(5));
//
//        return component;
//    }
//    //endregion
//}
