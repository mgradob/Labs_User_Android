package com.itesm.labs.labsuser.activities.rest.service;

import com.itesm.labs.labsuser.activities.rest.models.History;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by mgradob on 4/12/15.
 */
public interface RecordService {

    @GET("/detailhistory/")
    ArrayList<History> getRecordOf(@Query("id_student_fk") String studentId);

    @POST("/detailhistory/")
    void postRecordItem(@Body History item, Callback<Response> cb);
}
