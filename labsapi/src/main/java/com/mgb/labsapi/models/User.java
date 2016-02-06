package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mgradob on 12/29/2014.
 */
public class User {

    @SerializedName("name")
    private String userName;
    @SerializedName("last_name_1")
    private String userLastName1;
    @SerializedName("last_name_2")
    private String userLastName2;
    @SerializedName("id_student")
    private String userId;
    @SerializedName("career")
    private String userCareer;
    @SerializedName("id_credential")
    private long userUid;
    @SerializedName("mail")
    private String userMail;
    @SerializedName("labs")
    private ArrayList<String> allowedLabs;

    public User(Builder builder) {
        this.userName = builder.userName;
        this.userLastName1 = builder.userLastName1;
        this.userLastName2 = builder.userLastName2;
        this.userId = builder.userId;
        this.userCareer = builder.userCareer;
        this.userUid = builder.userUid;
        this.userMail = builder.userMail;
        this.allowedLabs = builder.allowedLabs;

    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName1() {
        return userLastName1;
    }

    public String getUserLastName2() {
        return userLastName2;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserCareer() {
        return userCareer;
    }

    public long getUserUid() {
        return userUid;
    }

    public String getUserMail() {
        return userMail;
    }

    public ArrayList<String> getAllowedLabs() {
        return allowedLabs;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userLastName1='" + userLastName1 + '\'' +
                ", userLastName2='" + userLastName2 + '\'' +
                ", userId='" + userId + '\'' +
                ", userCareer='" + userCareer + '\'' +
                ", userUid=" + userUid +
                ", userMail='" + userMail + '\'' +
                ", allowedLabs=" + allowedLabs +
                '}';
    }

    public static class Builder {
        private String userName;
        private String userLastName1;
        private String userLastName2;
        private String userId;
        private String userCareer;
        private long userUid;
        private String userMail;
        private ArrayList<String> allowedLabs;

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setUserLastName1(String userLastName1) {
            this.userLastName1 = userLastName1;
            return this;
        }

        public Builder setUserLastName2(String userLastName2) {
            this.userLastName2 = userLastName2;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserCareer(String userCareer) {
            this.userCareer = userCareer;
            return this;
        }

        public Builder setUserUid(long userUid) {
            this.userUid = userUid;
            return this;
        }

        public Builder setUserMail(String userMail) {
            this.userMail = userMail;
            return this;
        }

        public Builder setAllowedLabs(ArrayList<String> allowedLabs) {
            this.allowedLabs = allowedLabs;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
