package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

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

    public Laboratory() {

    }

    public Laboratory(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Laboratory{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
