package com.itesm.labs.labsuser.activities.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mgradob on 11/14/14.
 */
public class Laboratory implements Serializable {

    @SerializedName("name")
    public String name;
    @SerializedName("link")
    public String url;
    public Integer imageResource;
    public Integer colorResource;

    public Laboratory(String name, Integer imageResource, Integer colorResource) {
        this.name = name;
        this.imageResource = imageResource;
        this.colorResource = colorResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    public Integer getColorResource() {
        return colorResource;
    }

    public void setColorResource(Integer colorResource) {
        this.colorResource = colorResource;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imageResource=" + imageResource +
                ", colorResource=" + colorResource +
                '}';
    }
}
