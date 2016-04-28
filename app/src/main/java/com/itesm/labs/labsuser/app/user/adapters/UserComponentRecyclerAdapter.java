package com.itesm.labs.labsuser.app.user.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.application.LabsPreferences;
import com.itesm.labs.labsuser.app.bases.BaseRecyclerAdapter;
import com.itesm.labs.labsuser.app.bases.BaseViewHolder;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 7/1/15.
 */
public class UserComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, UserComponentRecyclerAdapter.ComponentViewHolder> {

    private static final String TAG = UserComponentRecyclerAdapter.class.getSimpleName();

    @Inject
    CartClient mCartClient;
    @Inject
    LabsPreferences mLabsPreferences;

    private ArrayList<CartItem> mUserCartItems = new ArrayList<>();

    public UserComponentRecyclerAdapter(Activity activity) {
        super(activity);

        getUserCart();
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_user_component, parent, false);
        return new ComponentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(DATA.get(position));
    }

    private void getUserCart() {
        mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mLabsPreferences.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<CartItem>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get user cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get user cart completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get user cart error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<CartItem> cartItems) {
                        mUserCartItems = cartItems;
                    }
                });
    }

    public class ComponentViewHolder extends BaseViewHolder<Component> {

        @Bind(R.id.component_item_name)
        TextView componentItemName;
        @Bind(R.id.component_item_note)
        TextView componentItemNote;
        @Bind(R.id.component_item_available)
        TextView componentItemAvailable;
        @Bind(R.id.component_item_in_cart)
        TextView componentItemInCart;
        @Bind(R.id.component_item_add)
        ImageButton componentItemAdd;
        @Bind(R.id.component_item_remove)
        ImageButton componentItemRemove;

        public ComponentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Component holderItem) {
            mModel = holderItem;

            componentItemName.setText(mModel.getName());
            componentItemNote.setText(mModel.getNote());


            int inCart = 0;
            for (CartItem cartItem : mUserCartItems) {
                if (cartItem.getComponentId() == mModel.getId()) {
                    inCart = cartItem.getQuantity();

                    mModel.setAvailable(mModel.getTotal() - cartItem.getQuantity());
                }
            }

            componentItemAvailable.setText(String.format(mContext.getString(R.string.component_list_item_available), mModel.getAvailable()));
            componentItemInCart.setText(String.format(mContext.getString(R.string.component_list_item_in_cart), inCart));
        }

        @OnClick({R.id.component_item_add, R.id.component_item_remove})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.component_item_add:
                    if (mModel.getAvailable() > 0) {
                        mModel.setAvailable(mModel.getAvailable() - 1);
                        addComponentToCart();
                    }
                    break;
                case R.id.component_item_remove:
                    if (mModel.getTotal() > mModel.getAvailable()) {
                        mModel.setAvailable(mModel.getAvailable() + 1);
                        removeComponentFromCart();
                    }
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        public void addComponentToCart() {
            CartItem itemInCart = null;
            for (CartItem cartItem : mUserCartItems) {
                if (cartItem.getComponentId() == mModel.getId()) {
                    itemInCart = cartItem;
                    itemInCart.setQuantity(itemInCart.getQuantity() + 1);
                    break;
                }
            }

            Observable<Response> cartitemObservable = (itemInCart != null) ? mCartClient.editCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), itemInCart.getCartId(), itemInCart)
                    : mCartClient.postNewCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(),
                    new CartItem.Builder()
                            .setStudentId(mLabsPreferences.getUserId())
                            .setComponentId(mModel.getId())
                            .setQuantity(1)
                            .setReady(false)
                            .setCheckout(false)
                            .build()
            );

            cartitemObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Response>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task update user cart started");
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "Task get user cart completed");

                            notifyItemChanged(DATA.indexOf(mModel));
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Task get user cart error: " + e.getMessage());

                        }

                        @Override
                        public void onNext(Response response) {

                        }
                    });
        }

        public void removeComponentFromCart() {
            CartItem itemInCart = null;
            for (CartItem cartItem : mUserCartItems) {
                if (cartItem.getComponentId() == mModel.getId()) {
                    itemInCart = cartItem;
                    break;
                }
            }
            if (itemInCart == null) return;

            Observable<Response> cartitemObservable;

            if (itemInCart.getQuantity() > 1) {
                itemInCart.setQuantity(itemInCart.getQuantity() - 1);
                cartitemObservable = mCartClient.editCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), itemInCart.getCartId(), itemInCart);
            } else {
                cartitemObservable = mCartClient.deleteCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), itemInCart.getCartId());
            }

            cartitemObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Response>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task update user cart started");
                        }

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "Task get user cart completed");

                            notifyItemChanged(DATA.indexOf(mModel));
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Task get user cart error: " + e.getMessage());

                        }

                        @Override
                        public void onNext(Response response) {

                        }
                    });
        }
    }
}
