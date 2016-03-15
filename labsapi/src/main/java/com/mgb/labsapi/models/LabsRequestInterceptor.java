package com.mgb.labsapi.models;

import retrofit.RequestInterceptor;

/**
 * Created by mgradob on 3/14/16.
 */
public class LabsRequestInterceptor implements RequestInterceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
    }
}
