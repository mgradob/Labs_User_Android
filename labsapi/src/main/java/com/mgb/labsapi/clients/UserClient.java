package com.mgb.labsapi.clients;

import com.mgb.labsapi.Constants;
import com.mgb.labsapi.models.Auth;
import com.mgb.labsapi.models.User;

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
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserClient {

    @POST("/auth/login/")
    Observable<Auth> loginUser(@Body LoginUser loginUser);

    @GET("/students/{user_id}")
    Observable<User> getUser(@Header(Constants.AUTHORIZATION) String token,
                             @Path(Constants.ID_USER) String userId);

    @GET("/students/")
    Observable<ArrayList<User>> getUsers(@Header(Constants.AUTHORIZATION) String token);

    @POST("/students/")
    Observable<Response> postNewUser(@Header(Constants.AUTHORIZATION) String token,
                                     @Body User body);

    @PUT("/students/{user_id}/")
    Observable<Response> editUser(@Header(Constants.AUTHORIZATION) String token,
                                  @Path(Constants.ID_USER) String userId,
                                  @Body User body);

    @DELETE("/students/{user_id}/")
    Observable<Response> deleteUser(@Header(Constants.AUTHORIZATION) String token,
                                    @Path(Constants.ID_USER) String userId);
}