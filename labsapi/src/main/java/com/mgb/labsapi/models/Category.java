package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by miguel on 7/10/14.
 */
public class Category {

    @SerializedName("id_category")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
