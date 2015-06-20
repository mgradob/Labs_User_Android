package com.itesm.labs.labsuser.activities.rest.service;


import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by miguel on 7/10/14.
 */
public interface CategoryService {

    @GET("/category/")
    ArrayList<Category> getCategories();

    @GET("/category/{id_category}")
    Category getCategory(@Path("id_category") int category);

    @POST("/category/")
    void postNewCategory(@Body Category body, Callback<Response> cb);

    @PUT("/category/{category_id}/")
    void editCategory(@Path("category_id") String catId, @Body Category body, Callback<Response> cb);

    @DELETE("/category/{category_id}/")
    void deleteCategory(@Path("category_id") String catId, Callback<Response> cb);
}
