package com.itesm.labs.labsuser.activities.rest.service;

import com.itesm.labs.labsuser.activities.rest.models.Component;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentService {

    @GET("/component/")
    ArrayList<Component> getAllComponents();

    @GET("/component/")
    ArrayList<Component> getComponents(@Query("id_category_fk") String category);

    @GET("/component/{id_component}")
    Component getComponent(@Path("id_component") int componentId);
}
