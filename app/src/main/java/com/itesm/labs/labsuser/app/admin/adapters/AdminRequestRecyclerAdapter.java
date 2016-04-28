package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.views.activities.RequestDetailActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;

import butterknife.Bind;

/**
 * Created by mgradob on 12/29/15.
 */
public class AdminRequestRecyclerAdapter extends BaseRecyclerAdapter<ItemUserCart, AdminRequestRecyclerAdapter.ViewHolder> {

    public AdminRequestRecyclerAdapter(Activity activity) {
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
        View view = inflater.inflate(R.layout.list_item_requests, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<ItemUserCart> {

        @Bind(R.id.request_item_image)
        ImageView requestItemImage;
        @Bind(R.id.request_item_user_name)
        TextView requestItemUserName;
        @Bind(R.id.request_item_user_id)
        TextView requestItemUserId;
        @Bind(R.id.request_item_user_date)
        TextView requestItemUserDate;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ItemUserCart holderItem) {
            mModel = holderItem;

            if (mModel.isReady()) {
                requestItemImage.setImageResource(R.drawable.ic_done_white);
                requestItemImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.request_indicator_done));
            } else {
                requestItemImage.setImageResource(R.drawable.ic_cancel_white);
                requestItemImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.request_indicator_pending));
            }

            requestItemUserName.setText(mModel.getUserName());
            requestItemUserId.setText(mModel.getUserId());
            requestItemUserDate.setText(mModel.getCartDate());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RequestDetailActivity.class);
            intent.putExtra(RequestDetailActivity.EXTRA_USER_ID, mModel.getUserId());
            intent.putExtra(RequestDetailActivity.EXTRA_IS_READY, mModel.isReady());
            mActivity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
