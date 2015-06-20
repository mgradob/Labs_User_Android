package com.itesm.labs.labsuser.activities.rest.queries;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.activities.rest.models.History;
import com.itesm.labs.labsuser.activities.rest.service.RecordService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/12/15.
 */
public class RecordQuery {

    private String ENDPOINT;

    public RecordQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public ArrayList<History> getRecordOf(String userId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        RecordService service = restAdapter.create(RecordService.class);

        return service.getRecordOf(userId);
    }
}
