package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 4/12/15.
 */
public class History {

    @SerializedName("id_student_fk")
    private String studentId;
    @SerializedName("id_component_fk")
    private int componentId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("date_out")
    private String dateOut;
    @SerializedName("date_in")
    private String dateIn;

    public History(Builder builder) {
        this.studentId = builder.studentId;
        this.componentId = builder.componentId;
        this.quantity = builder.quantity;
        this.dateOut = builder.dateOut;
        this.dateIn = builder.dateIn;

    }

    public String getStudentId() {
        return studentId;
    }

    public int getComponentId() {
        return componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDateOut() {
        return dateOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    @Override
    public String toString() {
        return "History{" +
                "studentId='" + studentId + '\'' +
                ", componentId=" + componentId +
                ", quantity=" + quantity +
                ", dateOut='" + dateOut + '\'' +
                ", dateIn='" + dateIn + '\'' +
                '}';
    }

    public static class Builder {
        private String studentId;
        private int componentId;
        private int quantity;
        private String dateOut;
        private String dateIn;

        public Builder setStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder setComponentId(int componentId) {
            this.componentId = componentId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setDateOut(String dateOut) {
            this.dateOut = dateOut;
            return this;
        }

        public Builder setDateIn(String dateIn) {
            this.dateIn = dateIn;
            return this;
        }

        public History build() {
            return new History(this);
        }
    }
}
