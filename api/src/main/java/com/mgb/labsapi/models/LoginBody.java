package com.mgb.labsapi.models;

/**
 * Created by mgradob on 2/7/16.
 */
public class LoginBody {

    private String id_student;
    private String password;

    public LoginBody(Builder builder) {
        this.id_student = builder.id_student;
        this.password = builder.password;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginBody{" +
                "id_student='" + id_student + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private String id_student;
        private String password;

        public Builder setId_student(String id_student) {
            this.id_student = id_student;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginBody build(){
            return new LoginBody(this);
        }
    }
}
