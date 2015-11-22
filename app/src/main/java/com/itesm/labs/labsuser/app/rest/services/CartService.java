package com.itesm.labs.labsuser.app.rest.services;

import com.itesm.labs.labsuser.app.rest.models.CartItem;

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

/**
 * Created by mgradob on 2/28/15.
 */
public interface CartService {

    @GET("/{lab}/detailcart/")
    ArrayList<CartItem> getCartsItems(@Header("Authorization") String token,
                                      @Path("lab") String lab);

    @GET("/{lab}/detailcart/")
    ArrayList<CartItem> getCartItemsOf(@Header("Authorization") String token,
                                       @Path("lab") String lab,
                                       @Query("id_student_fk") String userId);

    @POST("/{lab}/detailcart/")
    Response postNewCartItem(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Body CartItem item);

    @PUT("/{lab}/detailcart/{id_cart}/")
    Response editCartItem(@Header("Authorization") String token,
                      @Path("lab") String lab,
                      @Path("id_cart") int cartId,
                      @Body CartItem cartItemBody);

    @DELETE("/{lab}/detailcart/{id_cart}/")
    Response deleteCartItem(@Header("Authorization") String token,
                        @Path("lab") String lab,
                        @Path("id_cart") int cartId);
}
