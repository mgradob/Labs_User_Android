package com.itesm.labs.labsuser.app.admin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemRequestDetail;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mgradob on 12/27/15.
 */
public class RequestDetailRecyclerAdapter extends BaseRecyclerAdapter<ItemRequestDetail, RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public RequestDetailRecyclerAdapter() {
        super();
    }

    @Override
    public int getItemViewType(int position) {
        return DATA.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bindData(DATA.get(position));
        } else if (holder instanceof ItemViewHolder) {
            super.onBindViewHolder(holder, position);
            ((ItemViewHolder) holder).bindData(DATA.get(position));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.list_item_request_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.list_item_request_item, parent, false);
            return new ItemViewHolder(view);
        } else
            throw new RuntimeException("There is no type that matches viewType " + viewType);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.header_title)
        TextView headerTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ItemRequestDetail holderItem) {
            headerTitle.setText(holderItem.getHeaderTitle());
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.request_item_list_component_name)
        TextView mComponentName;
        @Bind(R.id.request_item_list_component_total)
        TextView mComponentTotal;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ItemRequestDetail holderItem) {
            mComponentName.setText(holderItem.getComponentName());
            mComponentTotal.setText(holderItem.getTotal());
        }
    }
}
