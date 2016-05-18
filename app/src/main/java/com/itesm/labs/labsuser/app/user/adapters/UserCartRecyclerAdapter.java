package com.itesm.labs.labsuser.app.user.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.mgb.labsapi.models.CartItem;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class UserCartRecyclerAdapter extends BaseRecyclerAdapter<CartItem, UserCartRecyclerAdapter.CartViewHolder> {

    public UserCartRecyclerAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_user_cart, parent, false);
        return new CartViewHolder(view);
    }

    public class CartViewHolder extends BaseViewHolder<CartItem> {

        @Bind(R.id.cart_component_name)
        TextView cartComponentName;
        @Bind(R.id.cart_component_note)
        TextView cartComponentNote;
        @Bind(R.id.cart_component_in_cart)
        TextView cartComponentInCart;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public CartViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(CartItem holderItem) {
            mModel = holderItem;

            cartComponentName.setText(String.format("%s %s", mModel.getCategoryName(), mModel.getComponentName()));
            cartComponentNote.setText(mModel.getComponentNote());
            cartComponentInCart.setText(String.format(mContext.getString(R.string.cart_qty_in_cart), holderItem.getQuantity()));
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
