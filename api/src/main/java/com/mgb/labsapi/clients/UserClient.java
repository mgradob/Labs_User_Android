package com.mgb.labsapi.clients;

import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.models.Auth;
import com.mgb.labsapi.models.ChangePassword;
import com.mgb.labsapi.models.LoginBody;
import com.mgb.labsapi.models.NewUser;
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

    @POST("/auth/register/")
    Observable<Response> registerUser(@Body NewUser body);

    @POST("/auth/login/")
    Observable<Auth> loginUser(@Body LoginBody loginUser);

    @POST("/auth/logout/")
    Observable<Response> logoutUser(@Header(ApiConstants.AUTHORIZATION) String token);

    @POST("/auth/password/")
    Observable<Response> changeUserPass(@Header(ApiConstants.AUTHORIZATION) String token,
                                        @Body ChangePassword body);

    @GET("/students/{user_id}")
    Observable<User> getUser(@Header(ApiConstants.AUTHORIZATION) String token,
                             @Path(ApiConstants.ID_USER) String userId);

    @GET("/students/")
    Observable<ArrayList<User>> getUsers(@Header(ApiConstants.AUTHORIZATION) String token);

    @POST("/students/")
    Observable<Response> postNewUser(@Header(ApiConstants.AUTHORIZATION) String token,
                                     @Body NewUser body);

    @PUT("/students/{user_id}/")
    Observable<Response> editUser(@Header(ApiConstants.AUTHORIZATION) String token,
                                  @Path(ApiConstants.ID_USER) String userId,
                                  @Body User body);

    @DELETE("/students/{user_id}/")
    Observable<Response> deleteUser(@Header(ApiConstants.AUTHORIZATION) String token,
                                    @Path(ApiConstants.ID_USER) String userId);
}