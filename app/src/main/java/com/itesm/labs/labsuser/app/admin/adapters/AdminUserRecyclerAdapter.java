package com.itesm.labs.labsuser.app.admin.adapters;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.events.ItemClickEvent;
import com.itesm.labs.labsuser.app.commons.events.ItemLongClickEvent;
import com.mgb.labsapi.models.User;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by miguel on 26/10/14.
 */
public class AdminUserRecyclerAdapter extends BaseRecyclerAdapter<User, AdminUserRecyclerAdapter.ViewHolder> {

    int[] mColors;
    Random mRandom;

    public AdminUserRecyclerAdapter() {
        super();

        mColors = mContext.getResources().getIntArray(R.array.material_colors);
        mRandom = new Random();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_itesm_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
        holder.itemView.setOnClickListener(v ->
                mEventBus.post(new ItemClickEvent<>(DATA.get(position))));
        holder.itemView.setOnLongClickListener(v -> {
            mEventBus.post(new ItemLongClickEvent<>(DATA.get(position)));
            return true;
        });
    }

    public class ViewHolder extends BaseViewHolder<User> {

        @Bind(R.id.user_item_user_initial)
        TextView mUserInitial;
        @Bind(R.id.user_item_user_name)
        TextView mUserName;
        @Bind(R.id.user_item_user_extra)
        TextView mUserExtra;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(User holderItem) {
            mUserInitial.setText("" + holderItem.getUserName().charAt(0));
            mUserName.setText(holderItem.getUserFullName());
            mUserExtra.setText(String.format(
                    mContext.getString(R.string.user_list_item_extra),
                    holderItem.getUserCareer(),
                    holderItem.getUserId()));

            GradientDrawable gradientDrawable = (GradientDrawable) mUserInitial.getBackground();
            gradientDrawable.setColor(mColors[mRandom.nextInt(mColors.length-1)]);
        }
    }
}
