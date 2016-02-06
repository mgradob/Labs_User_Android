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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
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
}
