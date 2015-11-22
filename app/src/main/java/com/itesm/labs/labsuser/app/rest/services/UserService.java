package com.itesm.labs.labsuser.app.rest.services;

import com.itesm.labs.labsuser.app.rest.models.Auth;
import com.itesm.labs.labsuser.app.rest.models.LoginUser;
import com.itesm.labs.labsuser.app.rest.models.User;

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
 * Created by mgrad_000 on 12/29/2014.
 */
public interface UserService {

    @POST("/auth/login/")
    Auth loginUser(@Body LoginUser loginUser);

    @GET("/students/{user_id}")
    User getUser(@Header("Authorization") String token,
                 @Path("user_id") String userId);

    @GET("/students/")
    ArrayList<User> getUsers(@Header("Authorization") String token);

    @POST("/students/")
    void postNewUser(@Header("Authorization") String token,
                     @Body User body,
                     Callback<Response> cb);

    @PUT("/students/{user_id}/")
    void editUser(@Header("Authorization") String token,
                  @Path("user_id") String userId,
                  @Body User body,
                  Callback<Response> cb);

    @DELETE("/students/{user_id}/")
    void deleteUser(@Header("Authorization") String token,
                    @Path("user_id") String userId,
                    Callback<Response> cb);
}
