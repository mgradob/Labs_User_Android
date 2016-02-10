package com.itesm.labs.labsuser.app.commons.adapters.models;

import com.itesm.labs.labsuser.R;
import com.mgb.labsapi.models.Laboratory;

/**
 * Created by mgradob on 2/6/16.
 */
public class ItemLaboratory {

    private Laboratory laboratory;
    private Integer imageResource;
    private Integer colorResource;

    public ItemLaboratory(Builder builder) {
        this.laboratory = builder.laboratory;
        this.imageResource = builder.imageResource;
        this.colorResource = builder.colorResource;
    }

    public static ItemLaboratory fromLaboratory(Laboratory laboratory) {
        return new ItemLaboratory.Builder()
                .setLaboratory(laboratory)
                .build();
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public Integer getColorResource() {
        return colorResource;
    }

    public static class Builder {
        private Laboratory laboratory;
        private Integer imageResource = R.drawable.ic_dummy_category_land;
        private Integer colorResource;

        public Builder setLaboratory(Laboratory laboratory) {
            this.laboratory = laboratory;
            return this;
        }

        public Builder setImageResource(Integer imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public Builder setColorResource(Integer colorResource) {
            this.colorResource = colorResource;
            return this;
        }

        public ItemLaboratory build() {
            return new ItemLaboratory(this);
        }
    }
}
