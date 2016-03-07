package com.mgb.labsapi;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by mgradob on 2/6/16.
 */
public class LabsErrorHandler implements ErrorHandler {

    private static final String TAG = LabsErrorHandler.class.getSimpleName();

    @Override
    public Throwable handleError(RetrofitError cause) {

        switch (cause.getKind()){
            case NETWORK:
                Log.e(TAG, "NETWORK ERROR");
                break;
            case CONVERSION:
                Log.e(TAG, "CONVERSION ERROR");
                break;
            case HTTP:
                Log.e(TAG, "HTTP ERROR");
                break;
            case UNEXPECTED:
                Log.e(TAG, "UNEXPECTED ERROR");
                break;
            default:
                Log.e(TAG, "UNKNOWN ERROR");
                break;
        }

        return new Exception();
    }
}
