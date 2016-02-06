package com.mgb.labsapi;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by mgradob on 2/6/16.
 */
public class LabsErrorHandler implements ErrorHandler {
    @Override
    public Throwable handleError(RetrofitError cause) {
        return null;
    }
}
