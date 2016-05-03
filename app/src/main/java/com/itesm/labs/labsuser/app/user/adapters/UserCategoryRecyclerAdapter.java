package com.itesm.labs.labsuser.app.user.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.user.views.activities.UserInventoryDetailActivity;

import butterknife.Bind;

/**
 * Created by mgradob on 7/1/15.
 */
public class UserCategoryRecyclerAdapter extends BaseRecyclerAdapter<ItemCategory, UserCategoryRecyclerAdapter.ViewHolder> {

    public UserCategoryRecyclerAdapter(Activity activity) {
        super(activity);
    }

    /**
     * Override this method so we can inflate our view.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.category_grid_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Override this method, calling super to add OnClick callback and later bind data to view.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    /**
     * ViewHolder for categories views.
     */
    public class ViewHolder extends BaseViewHolder<ItemCategory> {

        @Bind(R.id.cat_grid_item_image)
        ImageView categoryImage;

        @Bind(R.id.cat_grid_item_text)
        TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Insert data into views.
         *
         * @param category the category to fill data from.
         */
        @Override
        public void bindData(ItemCategory category) {
            mModel = category;

            Glide.with(mContext)
                    .load(mModel.getImageResource())
                    .into(categoryImage);

            categoryName.setText(mModel.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, UserInventoryDetailActivity.class);
            intent.putExtra(UserInventoryDetailActivity.EXTRA_CATEGORY_ID, mModel.getId());
            intent.putExtra(UserInventoryDetailActivity.EXTRA_CATEGORY_NAME, mModel.getName());
            intent.putExtra(UserInventoryDetailActivity.EXTRA_CATEGORY_IMAGE, mModel.getImageResource());
            mActivity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
