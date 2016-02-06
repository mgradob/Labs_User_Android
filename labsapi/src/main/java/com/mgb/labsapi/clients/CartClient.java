package com.mgb.labsapi.clients;

import com.mgb.labsapi.Constants;
import com.mgb.labsapi.models.CartItem;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mgradob on 2/28/15.
 */
public interface CartClient {

    @GET("/{lab}/detailcart/")
    Observable<ArrayList<CartItem>> getCartsItems(@Header(Constants.AUTHORIZATION) String token,
                                                  @Path(Constants.LAB) String lab);

    @GET("/{lab}/detailcart/")
    Observable<ArrayList<CartItem>> getCartItemsOf(@Header(Constants.AUTHORIZATION) String token,
                                                   @Path(Constants.LAB) String lab,
                                                   @Query(Constants.ID_STUDENT_FK) String userId);

    @POST("/{lab}/detailcart/")
    Observable<Response> postNewCartItem(@Header(Constants.AUTHORIZATION) String token,
                                         @Path(Constants.LAB) String lab,
                                         @Body CartItem item);

    @PUT("/{lab}/detailcart/{id_cart}/")
    Observable<Response> editCartItem(@Header(Constants.AUTHORIZATION) String token,
                                      @Path(Constants.LAB) String lab,
                                      @Path(Constants.ID_CART) int cartId,
                                      @Body CartItem cartItemBody);

    @DELETE("/{lab}/detailcart/{id_cart}/")
    Observable<Response> deleteCartItem(@Header(Constants.AUTHORIZATION) String token,
                                        @Path(Constants.LAB) String lab,
                                        @Path(Constants.ID_CART) int cartId);
}
