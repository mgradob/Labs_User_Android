package com.itesm.labs.labsuser.app.rest.services;

import com.itesm.labs.labsuser.app.rest.models.History;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mgradob on 4/12/15.
 */
public interface RecordService {

    @GET("/{lab}/detailhistory/")
    ArrayList<History> getRecordOf(@Header("Authorization") String token,
                                   @Path("lab") String lab,
                                   @Query("id_student_fk") String studentId);

    @POST("/{lab}/detailhistory/")
    void postRecordItem(@Header("Authorization") String token,
                        @Path("lab") String lab,
                        @Body History item, Callback<Response> cb);
}
