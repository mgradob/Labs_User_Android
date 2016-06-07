package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public class Component {

    @SerializedName("id_category_fk")
    private int category;
    @SerializedName("id_component")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("note")
    private String note;
    @SerializedName("total")
    private int total;
    @SerializedName("available")
    private int available;

    public Component(Builder builder) {
        this.category = builder.category;
        this.id = builder.id;
        this.name = builder.name;
        this.note = builder.note;
        this.total = builder.total;
        this.available = builder.available;
    }

    public int getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public int getTotal() {
        return total;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Component{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", available=" + available +
                '}';
    }

    public static class Builder {
        private int category;
        private int id;
        private String name;
        private String note;
        private int total;
        private int available;

        public Builder setCategory(int category) {
            this.category = category;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
            return this;
        }

        public Builder setTotal(int total) {
            this.total = total;
            return this;
        }

        public Builder setAvailable(int available) {
            this.available = available;
            return this;
        }

        public Component build() {
            return new Component(this);
        }
    }
}
