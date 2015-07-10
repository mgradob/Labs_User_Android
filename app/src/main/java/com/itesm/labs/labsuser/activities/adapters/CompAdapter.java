package com.itesm.labs.labsuser.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Cart;
import com.itesm.labs.labsuser.activities.rest.models.CartItem;
import com.itesm.labs.labsuser.activities.rest.models.Component;

import java.util.ArrayList;

/**
 * Created by miguel on 26/10/14.
 */
public class CompAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Component> DATA = new ArrayList<>();
    private Cart userCart;

    public CompAdapter(Context context, ArrayList<Component> data, Cart userCart) {
        this.context = context;
        this.DATA = data;
        this.userCart = userCart;
    }

    @Override
    public int getCount() {
        return DATA.size();
    }

    @Override
    public Object getItem(int position) {
        return DATA.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.inventory_list_item_component, parent, false);

            holder = new ViewHolder();
            holder.component_name = (TextView) convertView.findViewById(R.id.categories_component_name);
            holder.component_note = (TextView) convertView.findViewById(R.id.categories_component_note);
            holder.component_available = (TextView) convertView.findViewById(R.id.categories_component_available);
            holder.add_component = (ImageButton) convertView.findViewById(R.id.categories_add_btn);
            holder.remove_component = (ImageButton) convertView.findViewById(R.id.categories_remove_btn);
            holder.component_in_cart = (TextView) convertView.findViewById(R.id.categories_component_in_cart);

            //convertView.startAnimation(new AnimationUtils().loadAnimation(context, R.anim.categories_gridview_anim));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.component_name.setText(DATA.get(position).getName());
        holder.component_note.setText(DATA.get(position).getNote());
        holder.component_available.setText(" " + DATA.get(position).getAvailable());
        holder.add_component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem tempItem = new CartItem();
                tempItem.setComponentId(DATA.get(position).getId());

                for (CartItem item : userCart.getCartList()) {
                    if (item.getComponentId() == tempItem.getComponentId()) {
                        if(item.getQuantity() < DATA.get(position).getAvailable()) {
                            item.setQuantity(item.getQuantity() + 1);
                            holder.component_in_cart.setText(" " + item.getQuantity());
                        }

                        return;
                    }
                }

                tempItem.setQuantity(1);
                userCart.getCartList().add(tempItem);
                tempItem.setComponentName(DATA.get(position).getName());
                tempItem.setComponentNote(DATA.get(position).getNote());
                holder.component_in_cart.setText(" " + tempItem.getQuantity());
            }
        });
        holder.remove_component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem tempItem = new CartItem();
                tempItem.setComponentId(DATA.get(position).getId());

                for (CartItem item : userCart.getCartList()) {
                    if (item.getComponentId() == tempItem.getComponentId()) {
                        if (item.getQuantity() < 1) {
                            userCart.getCartList().remove(item);
                            holder.component_in_cart.setText(" " + 0);
                        } else {
                            item.setQuantity(item.getQuantity() - 1);
                            holder.component_in_cart.setText(" " + item.getQuantity());
                        }

                        return;
                    }
                }

                userCart.getCartList().add(tempItem);
            }
        });

        if (!userCart.getCartList().isEmpty()) {
            for (CartItem item : userCart.getCartList()) {
                if (item.getComponentId() == DATA.get(position).getId()) {
                    holder.component_in_cart.setText(" " + item.getQuantity());
                    break;
                } else holder.component_in_cart.setText(" " + 0);
            }
        } else holder.component_in_cart.setText(" " + 0);

        return convertView;
    }

    static class ViewHolder {
        TextView component_name, component_note, component_available, component_in_cart;
        ImageButton add_component, remove_component;
    }
}
