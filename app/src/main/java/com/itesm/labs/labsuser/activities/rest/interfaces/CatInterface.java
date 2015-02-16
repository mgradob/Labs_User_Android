package com.itesm.labs.labsuser.activities.rest.interfaces;

import com.itesm.labs.labsuser.activities.rest.models.Category;

import java.util.ArrayList;

import retrofit.http.GET;

/**
 * Created by mgradob on 2/15/15.
 */
public interface CatInterface {

    @GET("/category/?format=json")
    ArrayList<Category> getCat();
}
