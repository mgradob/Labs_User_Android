package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mgrad_000 on 12/29/2014.
 */
public class Admin {

    @SerializedName("id_administrator")
    private String adminId;
    @SerializedName("name")
    private String adminName;
    @SerializedName("last_name_1")
    private String adminLastName1;
    @SerializedName("last_name_2")
    private String adminLastName2;
    @SerializedName("mail")
    private String adminMail;
    @SerializedName("labs")
    private ArrayList<String> allowedLabs;

    public Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.adminName = builder.adminName;
        this.adminLastName1 = builder.adminLastName1;
        this.adminLastName2 = builder.adminLastName2;
        this.adminMail = builder.adminMail;
        this.allowedLabs = builder.allowedLabs;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminLastName1() {
        return adminLastName1;
    }

    public String getAdminLastName2() {
        return adminLastName2;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public ArrayList<String> getAllowedLabs() {
        return allowedLabs;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminLastName1='" + adminLastName1 + '\'' +
                ", adminLastName2='" + adminLastName2 + '\'' +
                ", adminMail='" + adminMail + '\'' +
                ", allowedLabs=" + allowedLabs +
                '}';
    }

    public static class Builder {
        private String adminId;
        private String adminName;
        private String adminLastName1;
        private String adminLastName2;
        private String adminMail;
        private ArrayList<String> allowedLabs;

        public Builder setAdminId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setAdminName(String adminName) {
            this.adminName = adminName;
            return this;
        }

        public Builder setAdminLastName1(String adminLastName1) {
            this.adminLastName1 = adminLastName1;
            return this;
        }

        public Builder setAdminLastName2(String adminLastName2) {
            this.adminLastName2 = adminLastName2;
            return this;
        }

        public Builder setAdminMail(String adminMail) {
            this.adminMail = adminMail;
            return this;
        }

        public Builder setAllowedLabs(ArrayList<String> allowedLabs) {
            this.allowedLabs = allowedLabs;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}