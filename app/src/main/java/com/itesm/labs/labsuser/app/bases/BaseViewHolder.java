package com.itesm.labs.labsuser.app.bases;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by mgradob on 11/19/15.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    /**
     * Constructor that binds to Butterknife automatically.
     *
     * @param itemView
     */
    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Bind data from the item to the views.
     *
     * @param holderItem
     */
    public abstract void bindData(T holderItem);
}
