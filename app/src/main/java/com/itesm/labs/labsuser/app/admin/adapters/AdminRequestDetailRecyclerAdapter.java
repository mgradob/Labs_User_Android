package com.itesm.labs.labsuser.app.admin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.mgb.labsapi.models.CartItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 12/27/15.
 */
public class AdminRequestDetailRecyclerAdapter extends BaseRecyclerAdapter<CartItem, AdminRequestDetailRecyclerAdapter.ViewHolder> {

    public AdminRequestDetailRecyclerAdapter() {
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
        View view = inflater.inflate(R.layout.list_item_request_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.request_item_list_component_name)
        TextView mComponentName;
        @Bind(R.id.request_item_list_component_total)
        TextView mComponentTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CartItem holderItem) {
            mComponentName.setText("" + holderItem.getComponentId());
            mComponentTotal.setText(String.format(mContext.getString(R.string.request_item_list_item_quantity), holderItem.getQuantity()));
        }
    }
}
