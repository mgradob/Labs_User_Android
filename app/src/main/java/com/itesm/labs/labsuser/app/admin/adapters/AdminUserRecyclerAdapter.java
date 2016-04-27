package com.itesm.labs.labsuser.app.admin.adapters;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.views.activities.UserDetailActivity;
import com.itesm.labs.labsuser.app.admin.views.activities.UserEditActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
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
            mModel = holderItem;

            mUserInitial.setText("" + mModel.getUserName().charAt(0));
            mUserName.setText(mModel.getUserFullName());
            mUserExtra.setText(String.format(
                    mContext.getString(R.string.user_list_item_extra),
                    mModel.getUserCareer(),
                    mModel.getUserId()));

            GradientDrawable gradientDrawable = (GradientDrawable) mUserInitial.getBackground();
            gradientDrawable.setColor(mColors[mRandom.nextInt(mColors.length-1)]);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, UserDetailActivity.class);
            intent.putExtra(UserEditActivity.EXTRA_USER_ID, mModel.getUserId());
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Intent intent = new Intent(mContext, UserEditActivity.class);
            intent.putExtra(UserEditActivity.EXTRA_USER_ID, mModel.getUserId());
            mContext.startActivity(intent);

            return true;
        }
    }
}
