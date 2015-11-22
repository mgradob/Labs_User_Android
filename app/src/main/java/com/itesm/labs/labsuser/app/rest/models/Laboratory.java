package com.itesm.labs.labsuser.app.rest.models;

import com.google.gson.annotations.SerializedName;
import com.itesm.labs.labsuser.R;

import java.io.Serializable;

/**
 * Created by mgradob on 11/14/14.
 */
public class Laboratory implements Serializable {

    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
    private int colorResource = 0;
    private Integer imageResource = R.drawable.ic_dummy_category_land;

    public Laboratory() {

    }

    public Laboratory(String name, Integer imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", colorResource=" + colorResource +
                ", imageResource=" + imageResource +
                '}';
    }
}
