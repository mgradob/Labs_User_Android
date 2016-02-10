package com.itesm.labs.labsuser.app.user.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itesm.labs.labsuser.R;
import com.mgb.labsapi.models.Category;

import butterknife.Bind;

/**
 * Created by mgradob on 7/1/15.
 */
public class CategoryRecyclerAdapter extends BaseRecyclerAdapter<Category, CategoryRecyclerAdapter.ViewHolder> {

    public CategoryRecyclerAdapter() {
        super();
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
        View itemView = inflater.inflate(R.layout.cat_grid_item, parent, false);
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

    // region ViewHolder

    /**
     * ViewHolder for categories views.
     */
    public class ViewHolder extends BaseViewHolder<Category> {
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
        public void bindData(Category category) {
            Glide.with(mContext)
                    .load(category.getImageResource())
                    .into(categoryImage);

            categoryName.setText(category.getName());
        }
    }
    //endregion
}
