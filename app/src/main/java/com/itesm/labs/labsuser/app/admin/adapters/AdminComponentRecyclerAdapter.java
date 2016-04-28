package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.Activity;
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

    public AdminComponentRecyclerAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_admin_component, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    public class ViewHolder extends BaseViewHolder<Component> {

        @Bind(R.id.component_item_name)
        TextView mComponentName;
        @Bind(R.id.component_item_note)
        TextView mComponentNote;
        @Bind(R.id.component_item_available)
        TextView mComponentAvailable;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Component holderItem) {
            mModel = holderItem;

            mComponentName.setText(mModel.getName());
            mComponentNote.setText(mModel.getNote());
            mComponentAvailable.setText(String.format(mContext.getString(R.string.component_list_item_available), mModel.getAvailable()));
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            // TODO: 4/23/16 go to edit component.
            return false;
        }
    }
}
