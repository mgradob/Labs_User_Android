package com.itesm.labs.labsuser.app.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.dialogs.EditComponentDialogFragment;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.contracts.IDialogContract;
import com.mgb.labsapi.models.Component;

import butterknife.Bind;

/**
 * Created by mgradob on 7/1/15.
 */
public class AdminComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, AdminComponentRecyclerAdapter.ViewHolder> {

    public AdminComponentRecyclerAdapter(BaseActivity mActivity) {
        super(mActivity);
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
            EditComponentDialogFragment fragment = EditComponentDialogFragment.newInstance(mModel);
            fragment.setContract(new IDialogContract() {
                @Override
                public void onActionSuccess() {
                    fragment.dismiss();
                }

                @Override
                public void onActionFailed() {
                    Toast.makeText(mContext, R.string.event_error_network, Toast.LENGTH_LONG)
                            .show();
                }
            });
            fragment.show(mActivity.getSupportFragmentManager(), null);

            return true;
        }
    }
}
