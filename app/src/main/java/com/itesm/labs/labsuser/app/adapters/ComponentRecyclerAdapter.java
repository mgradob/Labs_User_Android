package com.itesm.labs.labsuser.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.rest.models.CartItem;
import com.itesm.labs.labsuser.app.rest.models.Component;

import butterknife.Bind;

/**
 * Created by mgradob on 7/1/15.
 */
public class ComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, ComponentRecyclerAdapter.ViewHolder> {

    public ComponentRecyclerAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.inventory_list_item_component, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    //region ViewHolder
    public class ViewHolder extends BaseViewHolder<Component> {

        @Bind(R.id.categories_component_name)
        TextView mComponentName;
        @Bind(R.id.categories_component_note)
        TextView mComponentNote;
        @Bind(R.id.categories_component_available)
        TextView mComponentAvailable;
        @Bind(R.id.categories_component_in_cart)
        TextView mComponentInCart;
        @Bind(R.id.categories_add_btn)
        ImageButton mAddBtn;
        @Bind(R.id.categories_remove_btn)
        ImageButton mRemoveBtn;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(final Component holderItem) {
            mComponentName.setText(holderItem.getName());
            mComponentNote.setText(holderItem.getNote());
            mComponentAvailable.setText(String.valueOf(holderItem.getAvailable()));

            mAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem tempItem = new CartItem();
                    tempItem.setComponentId(holderItem.getId());

                    for (CartItem item : mAppGlobals.getUserCart().getCartList()) {
                        if (item.getComponentId() == tempItem.getComponentId()) {
                            if (item.getQuantity() < holderItem.getAvailable()) {
                                item.setQuantity(item.getQuantity() + 1);
                                mComponentInCart.setText(" " + item.getQuantity());
                            }

                            return;
                        }
                    }

                    tempItem.setQuantity(1);
                    mAppGlobals.getUserCart().getCartList().add(tempItem);
                    tempItem.setComponentName(holderItem.getName());
                    tempItem.setComponentNote(holderItem.getNote());
                    mComponentInCart.setText(" " + tempItem.getQuantity());
                }
            });

            mRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem tempItem = new CartItem();
                    tempItem.setComponentId(holderItem.getId());

                    for (CartItem item : mAppGlobals.getUserCart().getCartList()) {
                        if (item.getComponentId() == tempItem.getComponentId()) {
                            if (item.getQuantity() < 1) {
                                mAppGlobals.getUserCart().getCartList().remove(item);
                                mComponentInCart.setText(" " + 0);
                            } else {
                                item.setQuantity(item.getQuantity() - 1);
                                mComponentInCart.setText(" " + item.getQuantity());
                            }

                            return;
                        }
                    }

                    mAppGlobals.getUserCart().getCartList().add(tempItem);
                }
            });

            if (!mAppGlobals.getUserCart().getCartList().isEmpty()) {
                for (CartItem item : mAppGlobals.getUserCart().getCartList()) {
                    if (item.getComponentId() == holderItem.getId()) {
                        mComponentInCart.setText(" " + item.getQuantity());
                        break;
                    } else mComponentInCart.setText(" " + 0);
                }
            } else mComponentInCart.setText(" " + 0);
        }
    }
    //endregion
}
