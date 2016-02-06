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

    public Category(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
