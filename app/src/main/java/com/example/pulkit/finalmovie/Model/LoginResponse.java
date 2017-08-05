package com.example.pulkit.finalmovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pulkit on 8/5/2017.
 */

public class LoginResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("request_token")
    private String token;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @SerializedName("session_id")
    private String session;


    public LoginResponse() {
    }

    public LoginResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
