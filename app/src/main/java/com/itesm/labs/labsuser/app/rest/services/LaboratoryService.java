package com.itesm.labs.labsuser.app.rest.services;


import com.itesm.labs.labsuser.app.rest.models.Laboratory;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryService {

    @GET("/labs/")
    ArrayList<Laboratory> getLaboratories(@Header("Authorization") String token);

    @GET("/labs/{lab_name}")
    Laboratory getLaboratoryFromUrl(@Header("Authorization") String token,
                                    @Path("lab_name") String labName);
}
