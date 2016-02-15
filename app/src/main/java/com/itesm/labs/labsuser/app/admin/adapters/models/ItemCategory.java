package com.itesm.labs.labsuser.app.admin.adapters.models;

/**
 * Created by mgradob on 2/12/16.
 */
public class ItemCategory {

    private int id;
    private String name;
    private int imageResource;

    public ItemCategory(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageResource = builder.imageResource;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public static class Builder {
        private int id;
        private String name;
        private int imageResource;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setImageResource(int imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public ItemCategory build() {
            return new ItemCategory(this);
        }
    }
}
