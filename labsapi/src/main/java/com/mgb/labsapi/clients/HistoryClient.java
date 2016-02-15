package com.mgb.labsapi.clients;

import com.mgb.labsapi.Constants;
import com.mgb.labsapi.models.History;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mgradob on 4/12/15.
 */
public interface HistoryClient {

    @GET("/{lab}/detailhistory/")
    Observable<ArrayList<History>> getAllHistories(@Header(Constants.AUTHORIZATION) String token,
                                                   @Path(Constants.LAB) String lab);

    @GET("/{lab}/detailhistory/")
    Observable<ArrayList<History>> getHistoryOf(@Header(Constants.AUTHORIZATION) String token,
                                                @Path(Constants.LAB) String lab,
                                                @Query(Constants.ID_STUDENT_FK) String studentId);

    @POST("/{lab}/detailhistory/")
    Observable<Response> postHistoryItem(@Header(Constants.AUTHORIZATION) String token,
                                         @Path(Constants.LAB) String lab,
                                         @Body History item);
}
