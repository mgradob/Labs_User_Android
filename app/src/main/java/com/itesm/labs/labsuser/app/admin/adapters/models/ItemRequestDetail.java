package com.itesm.labs.labsuser.app.admin.adapters.models;

/**
 * Created by mgradob on 1/1/16.
 */
public class ItemRequestDetail {

    private boolean isHeader = false;
    private String headerTitle;
    private String componentName;
    // TODO: 1/3/16 add component note
    private int total;

    public boolean isHeader() {
        return isHeader;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getTotal() {
        return total;
    }

    public ItemRequestDetail(Builder builder) {
        this.isHeader = builder.isHeader;
        this.headerTitle = builder.headerTitle;
        this.componentName = builder.componentName;
        this.total = builder.total;
    }

    public static class Builder {
        private boolean isHeader;
        private String headerTitle;
        private String componentName;
        private int total;

        public Builder setIsHeader(boolean isHeader) {
            this.isHeader = isHeader;
            return this;
        }

        public Builder setHeaderTitle(String headerTitle) {
            this.headerTitle = headerTitle;
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

        public ItemRequestDetail build(){
            return new ItemRequestDetail(this);
        }
    }
}
