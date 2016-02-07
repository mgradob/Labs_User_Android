package com.mgb.labsapi.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mgradob on 11/7/15.
 */
public class Auth {

    @SerializedName("auth_token")
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
