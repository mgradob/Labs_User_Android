package com.mgb.labsapi.clients;


import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.models.Category;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by miguel on 7/10/14.
 */
public interface CategoryClient {

    @GET("/{lab}/category/")
    Observable<ArrayList<Category>> getCategories(@Header(ApiConstants.AUTHORIZATION) String token,
                                                  @Path(ApiConstants.LAB) String lab);

    @GET("/{lab}/category/{id_category}")
    Observable<Category> getCategory(@Header(ApiConstants.AUTHORIZATION) String token,
                                     @Path(ApiConstants.LAB) String lab,
                                     @Path(ApiConstants.ID_CATEGORY) int categoryId);

    @POST("/{lab}/category/")
    Observable<Response> postNewCategory(@Header(ApiConstants.AUTHORIZATION) String token,
                                         @Path(ApiConstants.LAB) String lab,
                                         @Body Category body);

    @PUT("/{lab}/category/{id_category}/")
    Observable<Response> editCategory(@Header(ApiConstants.AUTHORIZATION) String token,
                                      @Path(ApiConstants.LAB) String lab,
                                      @Path(ApiConstants.ID_CATEGORY) int categoryId,
                                      @Body Category body);

    @DELETE("/{lab}/category/{id_category}/")
    Observable<Response> deleteCategory(@Header(ApiConstants.AUTHORIZATION) String token,
                                        @Path(ApiConstants.LAB) String lab,
                                        @Path(ApiConstants.ID_CATEGORY) int categoryId);
}
