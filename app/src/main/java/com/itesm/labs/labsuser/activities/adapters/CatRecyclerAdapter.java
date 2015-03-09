package com.itesm.labs.labsuser.activities.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mgradob on 3/8/15.
 */
public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.ViewHolder> {

    private ArrayList<Category> mCategories;
    private Activity mActivity;

    public CatRecyclerAdapter(ArrayList<Category> mCategories, Activity mActivity) {
        this.mCategories = mCategories;
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_grid_item, parent, false);
        return new ViewHolder(view, mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.categoryImage.setImageDrawable(mActivity.getResources().getDrawable(category.getImageResource()));
        holder.categoryText.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryText;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            categoryImage = (ImageView) itemView.findViewById(R.id.cat_grid_item_image);
            categoryText = (TextView) itemView.findViewById(R.id.cat_grid_item_text);
        }
    }
}
