package com.itesm.labs.labsuser.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.rest.models.History;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by mgradob on 11/21/15.
 */
public class RecordRecyclerAdapter extends BaseRecyclerAdapter<History, RecordRecyclerAdapter.ViewHolder> {

    public RecordRecyclerAdapter() {
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
        View view = inflater.inflate(R.layout.record_list_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<History> {

        @Bind(R.id.record_item_category_name)
        TextView recordItemCategoryName;
        @Bind(R.id.record_item_component_name_note)
        TextView recordItemComponentNameNote;
        @Bind(R.id.record_item_component_quantity)
        TextView recordItemComponentQuantity;
        @Bind(R.id.record_item_date_out)
        TextView recordItemDateOut;
        @Bind(R.id.record_item_date_in)
        TextView recordItemDateIn;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(History holderItem) {
            recordItemCategoryName.setText(holderItem.getCategoryName());
            recordItemComponentNameNote.setText(holderItem.getComponentNameNote());
            recordItemComponentQuantity.setText("" + holderItem.getQuantity());
            recordItemDateOut.setText("Fecha pedido:\n" + holderItem.getDateOut());
            recordItemDateIn.setText((holderItem.getDateIn() != null) ? "Fecha entregado:\n" +  holderItem.getDateIn(): "Fecha entregado:\n - ");
        }
    }
}
