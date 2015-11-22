package com.itesm.labs.labsuser.app.rest.models;

import com.google.gson.annotations.SerializedName;
import com.itesm.labs.labsuser.R;

/**
 * Created by miguel on 7/10/14.
 */
public class Category {

    @SerializedName("id_category")
    private int id;
    @SerializedName("name")
    private String name;
    private Integer imageResource = R.drawable.ic_dummy_category;

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

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageResource=" + imageResource +
                '}';
    }
}
