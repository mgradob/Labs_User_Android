package com.mgb.labsapi.clients;

import com.mgb.labsapi.Constants;
import com.mgb.labsapi.models.Component;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mgrad_000 on 1/2/2015.
 */
public interface ComponentClient {

    @GET("/{lab}/component/")
    Observable<ArrayList<Component>> getAllComponents(@Header(Constants.AUTHORIZATION) String token,
                                                      @Path(Constants.LAB) String lab);

    @GET("/{lab}/component/")
    Observable<ArrayList<Component>> getComponents(@Header(Constants.AUTHORIZATION) String token,
                                                   @Path(Constants.LAB) String lab,
                                                   @Query(Constants.ID_CATEGORY_FK) int categoryFk);

    @GET("/{lab}/component/{id_component}")
    Observable<Component> getComponent(@Header(Constants.AUTHORIZATION) String token,
                                       @Path(Constants.LAB) String lab,
                                       @Path(Constants.ID_COMPONENT) int componentId);
}
