package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 5/2/16.
 */
public class NewUser {

    @SerializedName("id_student")
    private String userId;
    @SerializedName("name")
    private String userName;
    @SerializedName("last_name_1")
    private String userLastName1;
    @SerializedName("last_name_2")
    private String userLastName2;
    @SerializedName("career")
    private String career;
    @SerializedName("password")
    private String password;
    @SerializedName("mail")
    private String mail;

    public NewUser(String userId, String userName, String userLastName1, String userLastName2, String career, String password, String mail) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName1 = userLastName1;
        this.userLastName2 = userLastName2;
        this.career = career;
        this.password = password;
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName1() {
        return userLastName1;
    }

    public void setUserLastName1(String userLastName1) {
        this.userLastName1 = userLastName1;
    }

    public String getUserLastName2() {
        return userLastName2;
    }

    public void setUserLastName2(String userLastName2) {
        this.userLastName2 = userLastName2;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
