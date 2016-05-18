package com.itesm.labs.labsuser.app.admin.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemHistory;
import com.itesm.labs.labsuser.app.admin.views.activities.UserDetailActivity;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.itesm.labs.labsuser.app.commons.utils.DateTimeUtil;
import com.mgb.labsapi.clients.HistoryClient;
import com.mgb.labsapi.models.History;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/20/16.
 */
public class AdminUserDetailRecyclerAdapter extends BaseRecyclerAdapter<ItemHistory, AdminUserDetailRecyclerAdapter.ViewHolder> {

    private static final String TAG = AdminUserDetailRecyclerAdapter.class.getSimpleName();
    @Inject
    HistoryClient mHistoryClient;

    public AdminUserDetailRecyclerAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    public class ViewHolder extends BaseViewHolder<ItemHistory> {

        @Bind(R.id.record_item_category_name)
        TextView mCategoryName;
        @Bind(R.id.record_item_component_name_note_qty)
        TextView mComponentNameNoteQty;
        @Bind(R.id.record_item_date_out_in)
        TextView mDateOutIn;
        @Bind(R.id.record_item_deliver)
        Button recordItemDeliver;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ItemHistory holderItem) {
            mModel = holderItem;

            mCategoryName.setText(mModel.getCategoryName());
            mComponentNameNoteQty.setText(
                    String.format(mContext.getString(R.string.user_list_item_history_name_note_qty),
                            mModel.getComponentName(), mModel.getComponentNote(), mModel.getHistory().getQuantity())
            );
            mDateOutIn.setText(
                    String.format(mContext.getString(R.string.user_list_item_history_date_out_in),
                            DateTimeUtil.formatDateToLocal(mModel.getHistory().getDateOut()),
                            DateTimeUtil.formatDateToLocal(mModel.getHistory().getDateIn()))
            );
        }

        @Override
        @OnClick(R.id.record_item_deliver)
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.record_item_deliver:
                    deliverItem(mModel);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        private void deliverItem(ItemHistory item) {
            History history = new History.Builder()
                    .setHistoryId(item.getHistory().getHistoryId())
                    .setComponentId(item.getHistory().getComponentId())
                    .setQuantity(item.getHistory().getQuantity())
                    .setStudentId(item.getHistory().getStudentId())
                    .setDateOut(item.getHistory().getDateOut())
                    .setDateIn(DateTimeUtil.getCurrentDateTimeUtc())
                    .build();

            mHistoryClient.editHistoryItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), history.getHistoryId(), history)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Response>() {
                        @Override
                        public void onStart() {
                            Log.i(TAG, "onStart: task update history");
                        }

                        @Override
                        public void onCompleted() {
                            Log.i(TAG, "onCompleted: task update history");

                            ((UserDetailActivity) mActivity).reloadData();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: task update history", e);
                        }

                        @Override
                        public void onNext(Response response) {

                        }
                    });
        }
    }
}
