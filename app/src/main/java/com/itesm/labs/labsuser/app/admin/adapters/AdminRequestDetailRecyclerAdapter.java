package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCartDetail;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 12/27/15.
 */
public class AdminRequestDetailRecyclerAdapter extends BaseRecyclerAdapter<ItemUserCartDetail, AdminRequestDetailRecyclerAdapter.ViewHolder> {

    public AdminRequestDetailRecyclerAdapter(Activity activity) {
        super(activity);
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

    public class ViewHolder extends BaseViewHolder<ItemUserCartDetail> {

        @Bind(R.id.request_item_list_component_name)
        TextView mComponentName;
        @Bind(R.id.request_item_list_component_total)
        TextView mComponentTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ItemUserCartDetail holderItem) {
            mModel = holderItem;

            mComponentName.setText("" + mModel.getCategoryName() + " " + mModel.getComponentName());
            mComponentTotal.setText(String.format(mContext.getString(R.string.request_item_list_item_quantity), mModel.getCartItem().getQuantity()));
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
