package com.itesm.labs.labsuser.activities.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 2/15/15.
 */
public class Category {

    @SerializedName("name")
    private String name;
    @SerializedName("id_category")
    private Integer id;
    private Integer imageResource;

    public Category(String name, Integer id, Integer imageResource) {
        this.name = name;
        this.id = id;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", imageResource=" + imageResource +
                '}';
    }
}
