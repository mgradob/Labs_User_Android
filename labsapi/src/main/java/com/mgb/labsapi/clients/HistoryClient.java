package com.mgb.labsapi.clients;

import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.models.History;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mgradob on 4/12/15.
 */
public interface HistoryClient {

    @GET("/{lab}/detailhistory/")
    Observable<ArrayList<History>> getAllHistories(@Header(ApiConstants.AUTHORIZATION) String token,
                                                   @Path(ApiConstants.LAB) String lab);

    @GET("/{lab}/detailhistory/")
    Observable<ArrayList<History>> getHistoryOf(@Header(ApiConstants.AUTHORIZATION) String token,
                                                @Path(ApiConstants.LAB) String lab,
                                                @Query(ApiConstants.ID_STUDENT_FK) String studentId);

    @POST("/{lab}/detailhistory/")
    Observable<Response> postHistoryItem(@Header(ApiConstants.AUTHORIZATION) String token,
                                         @Path(ApiConstants.LAB) String lab,
                                         @Body History item);

    @PUT("/{lab}/detailhistory/{id_history}")
    Observable<Response> editHistoryItem(@Header(ApiConstants.AUTHORIZATION) String token,
                                         @Path(ApiConstants.LAB) String lab,
                                         @Path(ApiConstants.ID_HISTORY) int historyId,
                                         @Body History item);
}
