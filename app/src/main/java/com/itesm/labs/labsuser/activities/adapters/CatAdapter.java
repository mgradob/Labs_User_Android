package com.itesm.labs.labsuser.activities.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

/**
 * Created by mgradob on 2/15/15.
 */
public class CatAdapter extends BaseAdapter {

    private ArrayList<Category> categoryArrayList;
    private Context context;

    public CatAdapter(ArrayList<Category> categoryArrayList, Context context) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Category getItem(int position) {
        return categoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater.from(context));

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.cat_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.icono = (ImageView) convertView.findViewById(R.id.cat_grid_item_image);
            viewHolder.text = (TextView) convertView.findViewById(R.id.cat_grid_item_text);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.icono.setImageDrawable(convertView.getResources().getDrawable(categoryArrayList.get(position).getImageResource()));
        viewHolder.text.setText(categoryArrayList.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        ImageView icono;
        TextView text;
    }
}
