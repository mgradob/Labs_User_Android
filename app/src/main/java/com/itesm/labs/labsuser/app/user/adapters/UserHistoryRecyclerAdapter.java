package com.itesm.labs.labsuser.app.user.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.utils.DateTimeUtil;
import com.mgb.labsapi.models.History;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class UserHistoryRecyclerAdapter extends BaseRecyclerAdapter<History, UserHistoryRecyclerAdapter.ViewHolder> {

    public UserHistoryRecyclerAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_record, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<History> {

        @Bind(R.id.record_item_category_name)
        TextView recordItemCategoryName;
        @Bind(R.id.record_item_component_name_note_qty)
        TextView recordItemComponentNameNoteQty;
        @Bind(R.id.record_item_date_out_in)
        TextView recordItemDateOutIn;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(History holderItem) {
            mModel = holderItem;

            recordItemCategoryName.setText(mModel.getCategoryName());
            recordItemComponentNameNoteQty.setText(String.format(mContext.getString(R.string.record_name_note_quantity),
                    mModel.getComponentName() == null ? "" : mModel.getComponentName(),
                    mModel.getComponentNote() == null ? "" : mModel.getComponentNote(),
                    mModel.getQuantity())
            );
            recordItemDateOutIn.setText(
                    String.format(mContext.getString(R.string.record_dates),
                            DateTimeUtil.formatDateToLocal(mModel.getDateOut()),
                            DateTimeUtil.formatDateToLocal(mModel.getDateIn())));
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
