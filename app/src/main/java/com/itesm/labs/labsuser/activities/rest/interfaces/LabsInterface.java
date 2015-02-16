package com.itesm.labs.labsuser.activities.rest.interfaces;

import com.itesm.labs.labsuser.activities.rest.models.Laboratory;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgradob on 2/15/15.
 */
public interface LabsInterface {

    @GET("/links/?format=json")
    ArrayList<Laboratory> getLabs();
}
