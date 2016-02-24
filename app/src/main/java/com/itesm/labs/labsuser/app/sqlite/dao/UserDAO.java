//package com.itesm.labs.labsuser.app.sqlite.dao;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//
//import com.mgb.labsapi.models.Laboratory;
//import com.mgb.labsapi.models.User;
//import com.itesm.labs.labsuser.app.sqlite.LabsContract;
//
//import java.util.ArrayList;
//
///**
// * Created by mgradob on 12/27/15.
// */
//public class UserDAO extends BaseDAO {
//
//    public static final String TAG = UserDAO.class.getSimpleName();
//
//    public UserDAO() {
//        super();
//    }
//
//    public User getUser(String id) {
//        Cursor cursor = mDatabase.query(
//                LabsContract.UsersTable.TABLE,
//                new String[]{
//                        LabsContract.UsersTable.Columns.COLUMN_NAME,
//                        LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_1,
//                        LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_2,
//                        LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT,
//                        LabsContract.UsersTable.Columns.COLUMN_CAREER,
//                        LabsContract.UsersTable.Columns.COLUMN_ID_CREDENTIAL,
//                        LabsContract.UsersTable.Columns.COLUMN_MAIL
//                },
//                LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT + " =?",
//                new String[]{id},
//                null,
//                null,
//                null
//        );
//
//        if (cursor.moveToFirst()) {
//            return new User(
//                    cursor.getString(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3),
//                    cursor.getLong(4),
//                    cursor.getString(5),
//                    new ArrayList<Laboratory>()
//            );
//        }
//
//        return null;
//    }
//
//    public ArrayList<User> getUsers() {
//        Cursor cursor = mDatabase.query(
//                LabsContract.UsersTable.TABLE,
//                new String[]{
//                        LabsContract.UsersTable.Columns.COLUMN_NAME,
//                        LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_1,
//                        LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_2,
//                        LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT,
//                        LabsContract.UsersTable.Columns.COLUMN_CAREER,
//                        LabsContract.UsersTable.Columns.COLUMN_ID_CREDENTIAL,
//                        LabsContract.UsersTable.Columns.COLUMN_MAIL
//                },
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        ArrayList<User> users = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            do {
//                User user = new User(
//                        cursor.getString(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        cursor.getLong(4),
//                        cursor.getString(5),
//                        new ArrayList<Laboratory>()
//                );
//
//                users.add(user);
//            } while (cursor.moveToNext());
//
//            return users;
//        }
//
//        return null;
//    }
//
//    public long postUser(User user) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.UsersTable.Columns.COLUMN_NAME, user.getUserName());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_1, user.getUserLastName1());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT, user.getUserId());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_CAREER, user.getUserCareer());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_ID_CREDENTIAL, user.getUserUid());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_MAIL, user.getUserMail());
//
//        return mDatabase.insert(LabsContract.UsersTable.TABLE, null, values);
//    }
//
//    public long putUser(User user) {
//        ContentValues values = new ContentValues();
//        values.put(LabsContract.UsersTable.Columns.COLUMN_NAME, user.getUserName());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_LAST_NAME_1, user.getUserLastName1());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT, user.getUserId());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_CAREER, user.getUserCareer());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_ID_CREDENTIAL, user.getUserUid());
//        values.put(LabsContract.UsersTable.Columns.COLUMN_MAIL, user.getUserMail());
//
//        return mDatabase.update(
//                LabsContract.UsersTable.TABLE,
//                values,
//                LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT + " =?",
//                new String[]{user.getUserId()});
//    }
//
//    public long deleteUser(User user) {
//        return mDatabase.delete(
//                LabsContract.UsersTable.TABLE,
//                LabsContract.UsersTable.Columns.COLUMN_ID_STUDENT + " =?",
//                new String[]{user.getUserId()}
//        );
//    }
//}
