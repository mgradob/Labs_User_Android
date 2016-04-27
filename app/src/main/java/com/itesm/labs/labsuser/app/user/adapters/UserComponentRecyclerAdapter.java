package com.itesm.labs.labsuser.app.user.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class UserComponentRecyclerAdapter extends BaseRecyclerAdapter<Component, UserComponentRecyclerAdapter.ViewHolder> {

    private static final String TAG = UserComponentRecyclerAdapter.class.getSimpleName();

    @Inject
    ComponentClient mComponentClient;
    @Inject
    CartClient mCartClient;
    @Inject
    LabsPreferences mLabsPreferences;

    private ArrayList<CartItem> mUserCartItems = new ArrayList<>();

    public UserComponentRecyclerAdapter() {
        super();

        getUserCart();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_item_user_component, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    public class ViewHolder extends BaseViewHolder<Component> {

        @Bind(R.id.component_item_name)
        TextView componentItemName;
        @Bind(R.id.component_item_note)
        TextView componentItemNote;
        @Bind(R.id.component_item_available)
        TextView componentItemAvailable;
        @Bind(R.id.component_item_in_cart)
        TextView componentItemInCart;
        @Bind(R.id.component_item_add)
        TextView componentItemAdd;
        @Bind(R.id.component_item_remove)
        TextView componentItemRemove;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Component holderItem) {
            mModel = holderItem;

            componentItemName.setText(mModel.getName());
            componentItemNote.setText(mModel.getNote());

            componentItemAvailable.setText(String.format(mContext.getString(R.string.component_list_item_available), mModel.getAvailable()));
            componentItemInCart.setText(String.format(mContext.getString(R.string.component_list_item_in_cart), 0));
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
                    mModel.setAvailable(mModel.getAvailable() + 1);
                    removeComponentFromCart();
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

            Observable.zip(
                    mComponentClient.editComponent(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mModel.getId(), mModel),
                    cartitemObservable,
                    (component, response) -> component
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Component>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task update user cart started");
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
                        public void onNext(Component component) {

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

            if(itemInCart.getQuantity() > 1) {
                itemInCart.setQuantity(itemInCart.getQuantity() - 1);
                cartitemObservable = mCartClient.editCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), itemInCart.getCartId(), itemInCart);
            } else {
                cartitemObservable = mCartClient.deleteCartItem(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), itemInCart.getCartId());
            }

            Observable.zip(
                    mComponentClient.editComponent(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), mModel.getId(), mModel),
                    cartitemObservable,
                    (component, response) -> component
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Component>() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "Task update user cart started");
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
                        public void onNext(Component component) {

                        }
                    });
        }
    }
}
