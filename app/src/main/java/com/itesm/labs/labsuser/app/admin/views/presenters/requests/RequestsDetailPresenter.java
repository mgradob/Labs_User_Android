package com.itesm.labs.labsuser.app.admin.views.presenters.requests;

import android.util.Log;

import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCart;
import com.itesm.labs.labsuser.app.admin.adapters.models.ItemUserCartDetail;
import com.itesm.labs.labsuser.app.admin.views.fragments.requests.RequestsDetailFragment;
import com.itesm.labs.labsuser.app.bases.BaseFragmentPresenter;
import com.itesm.labs.labsuser.app.commons.utils.DateTimeUtil;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.clients.HistoryClient;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.CartItem;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;
import com.mgb.labsapi.models.History;
import com.mgb.labsapi.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 3/6/16.
 */
public class RequestsDetailPresenter extends BaseFragmentPresenter {

    private static final String TAG = RequestsDetailPresenter.class.getSimpleName();

    @Inject
    CartClient mCartClient;
    @Inject
    UserClient mUserClient;
    @Inject
    CategoryClient mCategoryClient;
    @Inject
    ComponentClient mComponentClient;
    @Inject
    HistoryClient mHistoryClient;

    private RequestsDetailFragment mView;
    private ItemUserCart mUserCart;

    private User mValidateUser;
    private ArrayList<ItemUserCartDetail> mUserCartDetails = new ArrayList<>();

    public RequestsDetailPresenter(RequestsDetailFragment mView, ItemUserCart cart) {
        super();
        this.mView = mView;
        this.mUserCart = cart;
    }

    /**
     * Gets the details of a user request.
     */
    public void getRequestDetail() {
        mSubscription.unsubscribe();
        mSubscription = Observable.zip(
                mCartClient.getCartItemsOf(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), mUserCart.getUserId()),
                mUserClient.getUser(mLabsPreferences.getToken(), mUserCart.getUserId()),
                mCategoryClient.getCategories(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink()),
                mComponentClient.getAllComponents(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink()),
                (cartItems, user, categories, components) -> {
                    mUserCartDetails.clear();

                    mValidateUser = user;

                    ItemUserCartDetail.Builder builder = new ItemUserCartDetail.Builder();

                    for (CartItem cartItem : cartItems) {
                        builder.setCartItem(cartItem);

                        for (Component component : components) {
                            if (component.getId() == cartItem.getComponentId()) {
                                builder.setComponentName(component.getName());

                                for (Category category : categories) {
                                    if (category.getId() == component.getCategory()) {
                                        builder.setCategoryName(category.getName());
                                    }
                                }
                            }
                        }

                        mUserCartDetails.add(builder.build());
                    }

                    return mUserCartDetails;
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ItemUserCartDetail>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Get carts task started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Get carts task finished");

                        mView.updateInfo(mUserCartDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Get carts task error: " + e.getMessage());

                        mView.showError(null); // TODO: 3/7/16 add error types.
                    }

                    @Override
                    public void onNext(ArrayList<ItemUserCartDetail> itemUserCartDetails) {
                        mUserCartDetails = itemUserCartDetails;
                    }
                });
    }

    /**
     * Ready the user cart.
     */
    public void readyUserCart() {
        mSubscription.unsubscribe();
        mSubscription = Observable.from(mUserCartDetails)
                .map(itemUserCartDetail -> {
                    itemUserCartDetail.getCartItem().setReady(true);
                    return itemUserCartDetail;
                })
                .flatMap(itemUserCartDetail1 -> mCartClient.editCartItem(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), itemUserCartDetail1.getCartItem().getCartId(), itemUserCartDetail1.getCartItem()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task validate cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task validate cart completed");

                        mView.showValidationSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task validate cart error: " + e.getMessage());

                        mView.showValidationError();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    /**
     * validates the user cart.
     */
    public void validateUserCart(long UID, boolean force) {
        if (!force) {
            if (UID != mValidateUser.getUserUid()) mView.showError(null);
            return;
        }

        mSubscription.unsubscribe();
        mSubscription = Observable.from(mUserCartDetails)
                .flatMap(itemUserCartDetail -> mHistoryClient.postHistoryItem(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), new History.Builder()
                                .setStudentId(itemUserCartDetail.getCartItem().getStudentId())
                                .setComponentId(itemUserCartDetail.getCartItem().getComponentId())
                                .setQuantity(itemUserCartDetail.getCartItem().getQuantity())
                                .setDateOut(DateTimeUtil.getCurrentDateTimeUtc())
                                .setDateIn(null)
                                .build()
                )
                        .flatMap(response -> mCartClient.deleteCartItem(mLabsPreferences.getToken(), mLabsPreferences.getCurrentLab().getLink(), itemUserCartDetail.getCartItem().getCartId())))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Response>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task validate cart started");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task validate cart completed");

                        mView.showValidationSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task validate cart error: " + e.getMessage());

                        mView.showValidationError();
                    }

                    @Override
                    public void onNext(List<Response> responses) {

                    }
                });
    }
}
