package com.itesm.labs.labsuser.app.application;

import android.content.SharedPreferences;

import com.itesm.labs.labsuser.R;
import com.mgb.labsapi.models.Laboratory;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mgradob on 12/7/15.
 */
public class LabsPreferences {

    /**
     * Shared preferences keys.
     */
    public static final String PREFERENCES_FILE_NAME = "labs_user_preferences";
    public static final String PREFERENCES_APP_FLOW = "app_flow";

    /**
     * User info.
     */
    public static final String PREFERENCES_KEY_USER_ID = "user_id";
    public static final String PREFERENCES_KEY_USER_PASS = "user_pass";
    public static final String PREFERENCES_KEY_USER_TOKEN = "token";
    public static final String PREFERENCES_KEY_USER_NAME = "name";
    public static final String PREFERENCES_KEY_USER_LAST_NAME_1 = "last_name_1";
    public static final String PREFERENCES_KEY_USER_LAST_NAME_2 = "last_name_2";
    public static final String PREFERENCES_KEY_USER_CAREER = "career";
    public static final String PREFERENCES_KEY_USER_ID_CREDENTIAL = "id_credential";
    public static final String PREFERENCES_KEY_USER_MAIL = "mail";
    public static final String PREFERENCES_KEY_USER_ALLOWED_LABS = "allowed_labs";
    public static final String PREFERENCES_KEY_USER_IS_ADMIN = "is_admin";
    public static final String PREFERENCES_KEY_USER_IS_LOGGED = "is_logged";
    public static final String PREFERENCES_KEY_USER_REMEMBER_INFO = "remember_info";

    /**
     * Lab info.
     */
    public static final String PREFERENCES_KEY_LAB_NAME = "lab_name";
    public static final String PREFERENCES_KEY_LAB_LINK = "lab_link";
    public static final String PREFERENCES_KEY_LAB_COLOR = "lab_color";

    private SharedPreferences mSharedPreferences;

    public LabsPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    //region User preferences,
    public User getUser() {
        User.Builder userBuilder = new User.Builder()
                .setUserName(mSharedPreferences.getString(PREFERENCES_KEY_USER_NAME, null))
                .setUserLastName1(mSharedPreferences.getString(PREFERENCES_KEY_USER_LAST_NAME_1, null))
                .setUserLastName2(mSharedPreferences.getString(PREFERENCES_KEY_USER_LAST_NAME_2, null))
                .setUserId(mSharedPreferences.getString(PREFERENCES_KEY_USER_ID, null))
                .setUserCareer(mSharedPreferences.getString(PREFERENCES_KEY_USER_CAREER, null))
                .setUserUid(mSharedPreferences.getLong(PREFERENCES_KEY_USER_ID_CREDENTIAL, -1))
                .setUserMail(mSharedPreferences.getString(PREFERENCES_KEY_USER_MAIL, null))
                .setIsAdmin(mSharedPreferences.getBoolean(PREFERENCES_KEY_USER_IS_ADMIN, false));

        ArrayList<String> labs = new ArrayList<>();
        Set<String> list = mSharedPreferences.getStringSet(PREFERENCES_KEY_USER_ALLOWED_LABS, null);

        if (list != null) {
            for (String lab : list)
                labs.add(lab);
        }
        userBuilder.setAllowedLabs(labs);

        return userBuilder.build();
    }

    public void putUser(User user) {
        mSharedPreferences.edit()
                .putString(PREFERENCES_KEY_USER_NAME, user.getUserName())
                .putString(PREFERENCES_KEY_USER_LAST_NAME_1, user.getUserLastName1())
                .putString(PREFERENCES_KEY_USER_LAST_NAME_2, user.getUserLastName2())
                .putString(PREFERENCES_KEY_USER_ID, user.getUserId())
                .putString(PREFERENCES_KEY_USER_CAREER, user.getUserCareer())
                .putLong(PREFERENCES_KEY_USER_ID_CREDENTIAL, user.getUserUid())
                .putString(PREFERENCES_KEY_USER_MAIL, user.getUserMail())
                .putStringSet(PREFERENCES_KEY_USER_ALLOWED_LABS, user.getAllowedLabs().isEmpty() ? new HashSet<>() : new HashSet<>(user.getAllowedLabs()))
                .putBoolean(PREFERENCES_KEY_USER_IS_ADMIN, user.isAdmin())
                .apply();
    }

    public String getUserId() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_ID, null);
    }

    public void putUserId(String id) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_ID, id).apply();
    }

    public String getUserPass() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_PASS, null);
    }

    public void putUserPass(String pass) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_PASS, pass).apply();
    }

    public String getToken() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_TOKEN, null);
    }

    public void putToken(String token) {
        String formattedToken = "Token " + token;
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_TOKEN, formattedToken).apply();
    }

    public String getUserName() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_NAME, null);
    }

    public void putUserName(String name) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_NAME, name).apply();
    }

    public String getUserLastName1() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_LAST_NAME_1, null);
    }

    public void putUserLastName1(String lastName1) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_LAST_NAME_1, lastName1).apply();
    }

    public String getUserLastName2() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_LAST_NAME_2, null);
    }

    public void putUserLastName2(String lastName2) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_LAST_NAME_2, lastName2).apply();
    }

    public String getUserCareer() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_CAREER, null);
    }

    public void putUserCareer(String career) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_CAREER, career).apply();
    }

    public long getUserIdCredential() {
        return mSharedPreferences.getLong(PREFERENCES_KEY_USER_ID_CREDENTIAL, -1);
    }

    public void putUserIdCredential(long idCredential) {
        mSharedPreferences.edit().putLong(PREFERENCES_KEY_USER_ID_CREDENTIAL, idCredential).apply();
    }

    public String getUserMail() {
        return mSharedPreferences.getString(PREFERENCES_KEY_USER_MAIL, null);
    }

    public void putUserMail(String mail) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_USER_MAIL, mail).apply();
    }

    public ArrayList<String> getUserAllowedLabs() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(mSharedPreferences.getStringSet(PREFERENCES_KEY_USER_ALLOWED_LABS, null));
        return arrayList;
    }

    public void putUserAllowedLabs(Set<String> labs) {
        mSharedPreferences.edit().putStringSet(PREFERENCES_KEY_USER_ALLOWED_LABS, labs).apply();
    }

    public boolean getIsAdmin() {
        return mSharedPreferences.getBoolean(PREFERENCES_KEY_USER_IS_ADMIN, false);
    }

    public void putIsAdmin(boolean isAdmin) {
        mSharedPreferences.edit().putBoolean(PREFERENCES_KEY_USER_IS_ADMIN, isAdmin).apply();
    }

    public boolean getIsLogged() {
        return mSharedPreferences.getBoolean(PREFERENCES_KEY_USER_IS_LOGGED, false);
    }

    public void putIsLogged(boolean isLogged) {
        mSharedPreferences.edit().putBoolean(PREFERENCES_KEY_USER_IS_LOGGED, isLogged).apply();
    }

    public boolean getRememberInfo() {
        return mSharedPreferences.getBoolean(PREFERENCES_KEY_USER_REMEMBER_INFO, false);
    }

    public void putRememberInfo(boolean remember) {
        mSharedPreferences.edit().putBoolean(PREFERENCES_KEY_USER_REMEMBER_INFO, remember).apply();
    }
    //endregion

    //region Lab preferences.
    public Laboratory getCurrentLab() {
        return new Laboratory.Builder()
                .setName(mSharedPreferences.getString(PREFERENCES_KEY_LAB_NAME, null))
                .setLink(mSharedPreferences.getString(PREFERENCES_KEY_LAB_LINK, null))
                .build();
    }

    public void putCurrentLab(Laboratory lab) {
        mSharedPreferences.edit()
                .putString(PREFERENCES_KEY_LAB_NAME, lab.getName())
                .putString(PREFERENCES_KEY_LAB_LINK, lab.getLink())
                .apply();
    }

    public String getLabName() {
        return mSharedPreferences.getString(PREFERENCES_KEY_LAB_NAME, null);
    }

    public void putLabName(String name) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_LAB_NAME, name).apply();
    }

    public String getLabLink() {
        return mSharedPreferences.getString(PREFERENCES_KEY_LAB_LINK, null);
    }

    public void putLabLink(String link) {
        mSharedPreferences.edit().putString(PREFERENCES_KEY_LAB_LINK, link).apply();
    }

    public int getLabColor() {
        return mSharedPreferences.getInt(PREFERENCES_KEY_LAB_COLOR, LabsApp.get().getResources().getColor(R.color.primary));
    }

    public void putLabColor(int color) {
        mSharedPreferences.edit()
                .putInt(PREFERENCES_KEY_LAB_COLOR, color)
                .apply();
    }
    //endregion
}
