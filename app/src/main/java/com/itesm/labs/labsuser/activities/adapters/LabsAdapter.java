package com.itesm.labs.labsuser.activities.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.rest.models.Laboratory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mgradob on 2/15/15.
 */
public class LabsAdapter extends BaseAdapter {

    private ArrayList<Laboratory> laboratoryArrayList;
    private Context context;
    private int colorArray[];

    public LabsAdapter(ArrayList<Laboratory> stringArrayList, Context context) {
        this.laboratoryArrayList = stringArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return laboratoryArrayList.size();
    }

    @Override
    public Laboratory getItem(int position) {
        return laboratoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater.from(context));

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.labs_grid_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.icono = (ImageView) convertView.findViewById(R.id.labs_grid_item_image);
            viewHolder.texto = (TextView) convertView.findViewById(R.id.labs_grid_item_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.icono.setImageDrawable(convertView.getResources().getDrawable(laboratoryArrayList.get(position).getImageResource()));
        viewHolder.texto.setText(laboratoryArrayList.get(position).getName());

        colorArray = convertView.getResources().getIntArray(R.array.material_colors);
        int color = new Random().nextInt(colorArray.length - 1);

        laboratoryArrayList.get(position).setColorResource(color);

        viewHolder.texto.setBackgroundColor(colorArray[color]);

        return convertView;
    }

    class ViewHolder {
        ImageView icono;
        TextView texto;
    }
}
