package com.itesm.labs.labsuser.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

/**
 * Created by mgradob on 7/1/15.
 */
public class CategoriesRecyclerAdapter extends RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<Category> mCategories;
    private MyOnClickListener myOnClickListener;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setmCategories(ArrayList<Category> mCategories) {
        this.mCategories = mCategories;
    }

    public void setMyOnClickListener(MyOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }

    public CategoriesRecyclerAdapter() {
    }

    public CategoriesRecyclerAdapter(ArrayList<Category> mCategories, Context mContext) {
        this.mCategories = mCategories;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cat_grid_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = 4;

        Bitmap icon = BitmapFactory.decodeResource(
                mContext.getResources(),
                mCategories.get(position).getImageResource(),
                options
        );
        holder.categoryIcon.setImageBitmap(icon);*/
        Glide.with(mContext)
                .load(mCategories.get(position).getImageResource())
                .into(holder.categoryIcon);
        holder.categoryName.setText(mCategories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryIcon;
        TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryIcon = (ImageView) itemView.findViewById(R.id.cat_grid_item_image);
            categoryName = (TextView) itemView.findViewById(R.id.cat_grid_item_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myOnClickListener.mOnClick(v, getLayoutPosition());
        }
    }

    public interface MyOnClickListener {
        void mOnClick(View v, int position);
    }
}
