package com.mgb.labsapi.models;

/**
 * Created by mgradob on 10/25/15.
 */
public class LoginUser {

    private String id_student;
    private String password;

    @Override
    public String toString() {
        return "LoginUser{" +
                "id_student='" + id_student + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
