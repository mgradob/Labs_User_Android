package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.mgb.labsapi.models.Laboratory;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by mgradob on 5/4/16.
 */
public class AdminUserAllowedLabsAdapter extends BaseRecyclerAdapter<Laboratory, AdminUserAllowedLabsAdapter.AllowedLabViewHolder> {

    public AdminUserAllowedLabsAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    public ArrayList<Laboratory> getData() {
        return (ArrayList<Laboratory>) DATA;
    }

    @Override
    public void onBindViewHolder(AllowedLabViewHolder holder, int position) {
        holder.bindData(DATA.get(position));
    }

    @Override
    public AllowedLabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new AllowedLabViewHolder(inflater.inflate(R.layout.list_item_allowed_lab, parent, false));
    }

    public class AllowedLabViewHolder extends BaseViewHolder<Laboratory> {

        @Bind(R.id.list_item_allowed_lab)
        CheckBox listItemAllowedLab;

        /**
         * Constructor that binds to Butterknife automatically and sets
         * {@link View.OnClickListener} and {@link View.OnLongClickListener}
         *
         * @param itemView
         */
        public AllowedLabViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Laboratory holderItem) {
            mModel = holderItem;

            listItemAllowedLab.setText(mModel.getName());
            listItemAllowedLab.setOnCheckedChangeListener((buttonView, isChecked) -> mModel.setAllowed(isChecked));
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
