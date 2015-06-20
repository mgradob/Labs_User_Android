package com.itesm.labs.labsuser.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Cart;
import com.itesm.labs.labsuser.activities.rest.models.CartItem;

/**
 * Created by miguel on 26/10/14.
 */
public class CartAdapter extends BaseAdapter {

    private Context context;
    private Cart userCart;

    public CartAdapter(Context context, Cart userCart) {
        this.context = context;
        this.userCart = userCart;
    }

    @Override
    public int getCount() {
        return userCart.getCartList().size();
    }

    @Override
    public Object getItem(int position) {
        return userCart.getCartList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater mLayoutInflater = (LayoutInflater.from(context));

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.cart_list_item_component, parent, false);

            holder = new ViewHolder();
            holder.component_name = (TextView) convertView.findViewById(R.id.cart_component_name);
            holder.component_note = (TextView) convertView.findViewById(R.id.cart_component_note);
            holder.add_component = (ImageButton) convertView.findViewById(R.id.cart_add_btn);
            holder.remove_component = (ImageButton) convertView.findViewById(R.id.cart_remove_btn);
            holder.component_in_cart = (TextView) convertView.findViewById(R.id.cart_component_in_cart);
            holder.component_ready = (ImageView) convertView.findViewById(R.id.cart_component_ready);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(userCart.getCartList().get(position).isReady()) holder.component_ready.setImageResource(R.drawable.ic_request_ready);
        else holder.component_ready.setImageResource(R.drawable.ic_request_pending);

        holder.component_name.setText(userCart.getCartList().get(position).getComponentName());
        holder.component_note.setText(userCart.getCartList().get(position).getComponentNote());
        holder.add_component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem tempItem = userCart.getCartList().get(position);
                tempItem.setQuantity(tempItem.getQuantity() + 1);
                holder.component_in_cart.setText("" + tempItem.getQuantity());
                holder.component_ready.setImageResource(R.drawable.ic_request_pending);
            }
        });
        holder.remove_component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem tempItem = userCart.getCartList().get(position);
                tempItem.setQuantity(userCart.getCartList().get(position).getQuantity() - 1);
                if(tempItem.getQuantity() < 1) {
                    userCart.getCartList().remove(tempItem);

                    notifyDataSetChanged();
                }
                else {
                    holder.component_in_cart.setText("" + tempItem.getQuantity());
                    holder.component_ready.setImageResource(R.drawable.ic_request_pending);
                }
            }
        });
        holder.component_in_cart.setText("" + userCart.getCartList().get(position).getQuantity());
        
        return convertView;
    }

    static class ViewHolder {
        ImageView component_ready;
        TextView component_name, component_note, component_in_cart;
        ImageButton add_component, remove_component;
    }
}
