package com.mgb.labsapi.clients;


import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.models.Laboratory;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryClient {

    @GET("/labs/")
    Observable<ArrayList<Laboratory>> getLaboratories(@Header(ApiConstants.AUTHORIZATION) String token);

    @GET("/labs/{lab_name}")
    Observable<Laboratory> getLaboratoryFromName(@Header(ApiConstants.AUTHORIZATION) String token,
                                                @Path(ApiConstants.LAB_NAME) String labName);
}
