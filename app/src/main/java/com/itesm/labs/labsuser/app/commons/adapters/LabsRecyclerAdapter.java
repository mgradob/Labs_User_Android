package com.itesm.labs.labsuser.app.commons.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.adapters.models.ItemLaboratory;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class LabsRecyclerAdapter extends BaseRecyclerAdapter<ItemLaboratory, LabsRecyclerAdapter.ViewHolder> {

    private int[] colorArray;

    public LabsRecyclerAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.labs_grid_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<ItemLaboratory> {

        @Bind(R.id.labs_grid_item_image)
        ImageView labsGridItemImage;
        @Bind(R.id.labs_grid_item_text)
        TextView labsGridItemText;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ItemLaboratory holderItem) {
            labsGridItemImage.setImageDrawable(mContext.getDrawable(holderItem.getImageResource()));
            labsGridItemText.setText(holderItem.getName());

            colorArray = mContext.getResources().getIntArray(R.array.material_colors);
            int color = new Random().nextInt(colorArray.length - 1);

            holderItem.setColorResource(color);

            labsGridItemText.setBackgroundColor(colorArray[color]);
        }
    }
}
