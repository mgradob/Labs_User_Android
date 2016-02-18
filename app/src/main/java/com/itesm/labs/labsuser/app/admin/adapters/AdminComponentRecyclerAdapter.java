package com.itesm.labs.labsuser.app.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.mgb.labsapi.models.Component;

import butterknife.Bind;

/**
 * Created by mgradob on 7/1/15.
 */
public class AdminComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, AdminComponentRecyclerAdapter.ViewHolder> {

    public AdminComponentRecyclerAdapter() {
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

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(final Component holderItem) {
            mComponentName.setText(holderItem.getName());
            mComponentNote.setText(holderItem.getNote());
            mComponentAvailable.setText(String.valueOf(holderItem.getAvailable()));
        }
    }
    //endregion
}
