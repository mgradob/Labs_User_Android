package com.itesm.labs.labsuser.app.rest.services;


import com.itesm.labs.labsuser.app.rest.models.Category;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by miguel on 7/10/14.
 */
public interface CategoryService {

    @GET("/{lab}/category/")
    ArrayList<Category> getCategories(@Header("Authorization") String token,
                                      @Path("lab") String lab);

    @GET("/{lab}/category/{id_category}")
    Category getCategory(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Path("id_category") int category);

    @POST("/{lab}/category/")
    void postNewCategory(@Header("Authorization") String token,
                         @Path("lab") String lab,
                         @Body Category body,
                         Callback<Response> cb);

    @PUT("/{lab}/category/{category_id}/")
    void editCategory(@Header("Authorization") String token,
                      @Path("lab") String lab,
                      @Path("category_id") String catId,
                      @Body Category body,
                      Callback<Response> cb);

    @DELETE("/{lab}/category/{category_id}/")
    void deleteCategory(@Header("Authorization") String token,
                        @Path("lab") String lab,
                        @Path("category_id") String catId,
                        Callback<Response> cb);
}
