package com.itesm.labs.labsuser.app.admin.adapters.models;

/**
 * Created by mgradob on 1/1/16.
 */
public class ItemRequestDetail {

    private String categoryName;
    private String componentName;
    private int total;

    public ItemRequestDetail() {
    }

    public ItemRequestDetail(Builder builder) {
        this.categoryName = builder.categoryName;
        this.componentName = builder.componentName;
        this.total = builder.total;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getTotal() {
        return total;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static class Builder {
        private String categoryName;
        private String componentName;
        private int total;

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder setComponentName(String componentName) {
            this.componentName = componentName;
            return this;
        }

        public Builder setTotal(int total) {
            this.total = total;
            return this;
        }

        public ItemRequestDetail build() {
            return new ItemRequestDetail(this);
        }
    }
}
