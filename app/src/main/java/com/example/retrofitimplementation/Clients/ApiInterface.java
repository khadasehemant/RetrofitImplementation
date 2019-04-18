package com.example.retrofitimplementation.Clients;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("books.json")
    Call<ResponseBody> get_books_data();
}


