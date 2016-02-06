package com.mgb.labsapi.clients;


import com.mgb.labsapi.Constants;
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
    Observable<ArrayList<Laboratory>> getLaboratories(@Header(Constants.AUTHORIZATION) String token);

    @GET("/labs/{lab_name}")
    Observable<Laboratory> getLaboratoryFromUrl(@Header(Constants.AUTHORIZATION) String token,
                                                @Path(Constants.LAB_NAME) String labName);
}
