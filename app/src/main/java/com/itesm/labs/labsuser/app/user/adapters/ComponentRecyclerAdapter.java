package com.itesm.labs.labsuser.app.user.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.Component;
import com.itesm.labs.labsuser.app.sqlite.dao.CartDAO;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mgradob on 7/1/15.
 */
public class ComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, ComponentRecyclerAdapter.ViewHolder> {

    @Inject
    CartDAO mCartDAO;

    HashMap<Integer, CartItem> actualUserCart = new HashMap<>();

    public ComponentRecyclerAdapter() {
        super();

        for (CartItem item : mCartDAO.getCart()) {
            actualUserCart.put(item.getComponentId(), item);
        }
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

            if (actualUserCart.containsKey(holderItem.getId()))
                mComponentInCart.setText(actualUserCart.get(holderItem.getId()).getQuantity());
            else
                mComponentInCart.setText(String.valueOf(0));
        }

        @OnClick(R.id.categories_add_btn)
        void onAddItemClick(Component component) {
            if (actualUserCart.containsKey(component.getId())) {
                CartItem temp = actualUserCart.get(component.getId());

                if (temp.getQuantity() < component.getAvailable()) {
                    temp.setQuantity(temp.getQuantity() + 1);

                    mComponentInCart.setText(String.valueOf(temp.getQuantity()));

                    mCartDAO.putCartItem(temp);
                }
            } else {
                CartItem temp = new CartItem();
                temp.setStudentId(mLabsPreferences.getUser().getUserId());
                temp.setCheckout(false);
                temp.setReady(false);
                temp.setQuantity(1);
                temp.setComponentId(component.getId());

                mCartDAO.postCartItem(temp);

                mComponentInCart.setText(String.valueOf(temp.getQuantity()));
            }
        }

        @OnClick(R.id.categories_remove_btn)
        void onRemoveItemClick(Component component) {
            if (actualUserCart.containsKey(component.getId())) {
                CartItem temp = actualUserCart.get(component.getId());

                if (temp.getQuantity() > 0) {
                    temp.setQuantity(temp.getQuantity() - 1);

                    mCartDAO.putCartItem(temp);
                } else {
                    temp.setQuantity(0);

                    mCartDAO.deleteCartItem(temp);
                }

                mComponentInCart.setText(String.valueOf(temp.getQuantity()));
            }
        }
    }
    //endregion
}
