package com.itesm.labs.labsuser.app.commons.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.adapters.models.ItemLaboratory;
import com.itesm.labs.labsuser.app.commons.views.activities.MainActivity;
import com.itesm.labs.labsuser.app.user.views.activities.UserMainActivity;
import com.mgb.labsapi.models.Laboratory;

import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class LabsRecyclerAdapter extends BaseRecyclerAdapter<ItemLaboratory, LabsRecyclerAdapter.ViewHolder> {

    @Inject
    LabsPreferences mLabsPreferences;

    private int[] colorArray;
    private Random mRandom = new Random();

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
            mModel = holderItem;

            labsGridItemImage.setImageDrawable(mContext.getDrawable(mModel.getImageResource()));
            labsGridItemText.setText(mModel.getLaboratory().getName());

            colorArray = mContext.getResources().getIntArray(R.array.material_colors);
            int color = colorArray[mRandom.nextInt(colorArray.length - 1)];

            mModel.setColorResource(color);

            labsGridItemText.setBackgroundColor(color);
        }

        @Override
        public void onClick(View v) {
            String[] params = mModel.getLaboratory().getLink().split("/");
            mModel = new ItemLaboratory.Builder()
                    .setLaboratory(new Laboratory.Builder()
                            .setName(mModel.getLaboratory().getName())
                            .setLink(params[params.length - 1])
                            .build())
                    .setColorResource(mModel.getColorResource())
                    .setImageResource(mModel.getImageResource())
                    .build();

            mLabsPreferences.putLabLink(params[params.length - 1]);
            mLabsPreferences.putCurrentLab(mModel.getLaboratory());
            mLabsPreferences.putLabColor(mModel.getColorResource());

            Intent intent;
            intent = mLabsPreferences.getIsAdmin() ? new Intent(mContext, MainActivity.class)
                    : new Intent(mContext, UserMainActivity.class) ;
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
