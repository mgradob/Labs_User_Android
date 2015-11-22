package com.itesm.labs.labsuser.app.rest.services;

import com.itesm.labs.labsuser.app.rest.models.Component;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    @GET("/{lab}/component/")
    ArrayList<Component> getAllComponents(@Header("Authorization") String token,
                                          @Path("lab") String lab);

    @GET("/{lab}/component/")
    ArrayList<Component> getComponents(@Header("Authorization") String token,
                                       @Path("lab") String lab,
                                       @Query("id_category_fk") int category);

    @GET("/{lab}/component/{id_component}")
    Component getComponent(@Header("Authorization") String token,
                           @Path("lab") String lab,
                           @Path("id_component") int componentId);
}
