package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mgradob on 11/14/14.
 */
public class Laboratory implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;

    private boolean isAllowed = false;

    public Laboratory(Builder builder) {
        this.name = builder.name;
        this.link = builder.link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    @Override
    public String toString() {
        return "Laboratory{" +
                "name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private String link;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Laboratory build() {
            return new Laboratory(this);
        }
    }
}
