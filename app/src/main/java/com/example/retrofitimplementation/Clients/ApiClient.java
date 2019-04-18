package com.example.retrofitimplementation.Clients;

import android.app.Activity;
import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL =  "https://www.wits-interactive.com/ftp/test/";
    private static Retrofit retrofit = null;

    private static Retrofit client() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ApiInterface getClient() {
        return client().create(ApiInterface.class);
    }

    public void apiRequest(Call<okhttp3.ResponseBody> apiCall, final ApiCallback cb , final int number, final Activity activity){
        apiCall.enqueue(new Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<okhttp3.ResponseBody> call, @NonNull Response<okhttp3.ResponseBody> response) {
                if (response.isSuccessful()) cb.onResponse(response,number,activity);
                else cb.onError(response, number,activity);
            }

            @Override
            public void onFailure(@NonNull Call<okhttp3.ResponseBody> call, @NonNull Throwable t) {
                cb.onFailure(t, number,activity);
            }
        });
    }

}


