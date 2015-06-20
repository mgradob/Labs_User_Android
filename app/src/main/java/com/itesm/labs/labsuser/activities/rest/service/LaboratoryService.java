package com.itesm.labs.labsuser.activities.rest.service;


import com.itesm.labs.labsuser.activities.rest.models.Laboratory;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgradob on 11/14/14.
 */
public interface LaboratoryService {

    @GET("/labs/")
    ArrayList<Laboratory> getLaboratories();

    @GET("/")
    Laboratory getLaboratoriesFromUrl();
}
