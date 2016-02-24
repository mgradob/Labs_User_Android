package com.itesm.labs.labsuser.app.admin.adapters.models;

import com.mgb.labsapi.models.History;

/**
 * Created by mgradob on 2/20/16.
 */
public class ItemHistory {

    private History history;
    private String componentName;
    private String componentNote;
    private String categoryName;

    public ItemHistory(Builder builder) {
        this.history = builder.history;
        this.componentName = builder.componentName;
        this.componentNote = builder.componentNote;
        this.categoryName = builder.categoryName;
    }

    public History getHistory() {
        return history;
    }

    public String getComponentName() {
        return componentName;
    }

    public String getComponentNote() {
        return componentNote;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return "ItemHistory{" +
                "history=" + history +
                ", componentName='" + componentName + '\'' +
                ", componentNote='" + componentNote + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

    public static class Builder {
        private History history;
        private String componentName;
        private String componentNote;
        private String categoryName;

        public Builder setHistory(History history) {
            this.history = history;
            return this;
        }

        public Builder setComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public Builder setComponentNote(String componentNote) {
            this.componentNote = componentNote;
            return this;
        }

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ItemHistory build() {
            return new ItemHistory(this);
        }
    }
}
