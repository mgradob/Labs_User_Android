package com.itesm.labs.labsuser.app.admin.adapters;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.views.activities.RequestDetailActivity;
import com.itesm.labs.labsuser.app.bases.BaseActivity;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.mgb.labsapi.clients.CartClient;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 12/29/15.
 */
public class AdminRequestRecyclerAdapter extends BaseRecyclerAdapter<ItemUserCart, AdminRequestRecyclerAdapter.ViewHolder> {

    private static final String TAG = AdminRequestRecyclerAdapter.class.getSimpleName();

    @Inject
    CartClient mCartClient;

    IRequestsCallback mIRequestsCallback;

    public AdminRequestRecyclerAdapter(BaseActivity mActivity, IRequestsCallback mIRequestsCallback) {
        super(mActivity);
        this.mIRequestsCallback = mIRequestsCallback;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_requests, parent, false);
        return new ViewHolder(view);
    }

    private void deleteCartFrom(String userId) {
        mActivity.showProgressDialog(R.string.request_item_delete_dialog_title, R.string.request_item_delete_dialog_body);

        mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), userId)
                .flatMap(Observable::from)
                .flatMap(cartItem -> mCartClient.deleteCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), cartItem.getCartId())
                        .doOnNext(response -> Log.i(TAG, "deleteCartFrom: " + cartItem.getCartId() + " deleted"))
                )
                .doOnError(throwable1 -> {
                    mActivity.dismissProgressDialog();
                    Toast.makeText(mContext, R.string.event_error_network, Toast.LENGTH_SHORT);
                })
                .doOnCompleted(() -> {
                    mActivity.dismissProgressDialog();
                    mIRequestsCallback.onRequestDeleted();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public class ViewHolder extends BaseViewHolder<ItemUserCart> {

        @Bind(R.id.request_item_image)
        ImageView requestItemImage;
        @Bind(R.id.request_item_user_name)
        TextView requestItemUserName;
        @Bind(R.id.request_item_user_id)
        TextView requestItemUserId;
        @Bind(R.id.request_item_user_date)
        TextView requestItemUserDate;

        /**
         * Constructor that binds to Butterknife automatically.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(ItemUserCart holderItem) {
            mModel = holderItem;

            if (mModel.isReady()) {
                requestItemImage.setImageResource(R.drawable.ic_done_white);
                requestItemImage.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.request_indicator_done));
            } else {
                requestItemImage.setImageResource(R.drawable.ic_cancel_white);
                requestItemImage.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.request_indicator_pending));
            }

            requestItemUserName.setText(mModel.getUserName());
            requestItemUserId.setText(mModel.getUserId());
            requestItemUserDate.setText(mModel.getCartDate());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RequestDetailActivity.class);
            intent.putExtra(RequestDetailActivity.EXTRA_USER_ID, mModel.getUserId());
            intent.putExtra(RequestDetailActivity.EXTRA_USER_NAME, mModel.getUserName());
            intent.putExtra(RequestDetailActivity.EXTRA_IS_READY, mModel.isReady());
            mActivity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                    .setMessage("Quieres eliminar el pedido?")
                    .setPositiveButton(R.string.action_delete, (dialog1, which) -> {
                        deleteCartFrom(mModel.getUserId());
                    })
                    .setNegativeButton(R.string.action_cancel, (dialog1, which) -> {
                        dialog1.dismiss();
                    })
                    .show();

            return true;
        }
    }

    public interface IRequestsCallback {

        /**
         * Callback invoked when a Cart has been deleted.
         */
        void onRequestDeleted();
    }
}
