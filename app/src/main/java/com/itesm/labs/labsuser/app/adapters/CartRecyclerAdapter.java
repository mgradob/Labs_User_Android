package com.itesm.labs.labsuser.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.rest.models.CartItem;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class CartRecyclerAdapter extends BaseRecyclerAdapter<CartItem, CartRecyclerAdapter.ViewHolder> {

    public CartRecyclerAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.cart_list_item_component, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<CartItem> {

        @Bind(R.id.cart_component_ready)
        ImageView cartComponentReady;
        @Bind(R.id.cart_component_name)
        TextView cartComponentName;
        @Bind(R.id.cart_component_note)
        TextView cartComponentNote;
        @Bind(R.id.cart_component_in_cart)
        TextView cartComponentInCart;
        @Bind(R.id.cart_add_btn)
        ImageButton cartAddBtn;
        @Bind(R.id.cart_remove_btn)
        ImageButton cartRemoveBtn;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(final CartItem holderItem) {
            cartComponentReady.setImageResource(holderItem.isReady() ? R.drawable.ic_request_ready : R.drawable.ic_request_pending);

            cartComponentName.setText(holderItem.getComponentName());
            cartComponentNote.setText(holderItem.getComponentNote());
            cartAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holderItem.setQuantity(holderItem.getQuantity() + 1);
                    cartComponentInCart.setText("" + holderItem.getQuantity());
                    cartComponentReady.setImageResource(R.drawable.ic_request_pending);
                }
            });
            cartRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holderItem.setQuantity(holderItem.getQuantity() - 1);
                    if (holderItem.getQuantity() < 1) {
                        DATA.remove(holderItem);
                        mAppGlobals.getUserCart().getCartList().remove(holderItem);

                        notifyDataSetChanged();
                    } else {
                        cartComponentInCart.setText("" + holderItem.getQuantity());
                        cartComponentReady.setImageResource(R.drawable.ic_request_pending);
                    }
                }
            });
            cartComponentInCart.setText("" + holderItem.getQuantity());
        }
    }
}
