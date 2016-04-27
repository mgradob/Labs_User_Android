package com.itesm.labs.labsuser.app.admin.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemCategory;
import com.itesm.labs.labsuser.app.admin.views.activities.InventoryDetailActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;

import butterknife.Bind;

/**
 * Created by mgradob on 2/12/16.
 */
public class AdminCategoryRecyclerAdapter extends BaseRecyclerAdapter<ItemCategory, AdminCategoryRecyclerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    public class ViewHolder extends BaseViewHolder<ItemCategory> {

        @Bind(R.id.category_list_item_image)
        ImageView itemImage;
        @Bind(R.id.category_list_item_name)
        TextView itemName;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ItemCategory holderItem) {
            mModel = holderItem;

            Glide.with(mContext)
                    .load(mModel.getImageResource())
                    .into(itemImage);

            itemName.setText(mModel.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, InventoryDetailActivity.class);
            intent.putExtra(InventoryDetailActivity.EXTRA_CATEGORY_ID, mModel.getId());
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            // TODO: 4/23/16 go to edit category.
            return false;
        }
    }
}
