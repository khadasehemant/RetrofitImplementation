package com.example.retrofitimplementation.Clients;

import android.app.Activity;

import retrofit2.Response;

public interface ApiCallback {
    void onResponse(Response<okhttp3.ResponseBody> res, int number, Activity activity);
    void onError(Response<okhttp3.ResponseBody> res, int number, Activity activity);
    void onFailure(Throwable err, int number, Activity activity);
}
