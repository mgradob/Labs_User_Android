package com.itesm.labs.labsuser.app.commons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.mgb.labsapi.models.Laboratory;

import butterknife.Bind;

/**
 * Created by mgradob on 12/11/15.
 */
public class SignupLabsRecyclerAdapter extends BaseRecyclerAdapter<Laboratory, SignupLabsRecyclerAdapter.ViewHolder> {

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.labs_checkbox_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<Laboratory> {

        @Bind(R.id.labs_checkbox_item_checkbox)
        CheckBox labsCheckboxItemCheckbox;
        @Bind(R.id.labs_checkbox_item_title)
        TextView labsCheckboxItemTitle;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(final Laboratory holderItem) {
            labsCheckboxItemTitle.setText(holderItem.getName());
            labsCheckboxItemCheckbox.setChecked(holderItem.isSignupChecked());
            labsCheckboxItemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holderItem.setIsSignupChecked(isChecked);
                }
            });
        }
    }
}
