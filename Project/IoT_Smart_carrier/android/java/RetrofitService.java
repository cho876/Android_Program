package com.example.skcho.smartcarrier;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Cho on 2017-12-28.
 */

public interface RetrofitService {
    @GET("/getCartInfoByJson.php")
    Call<CartDAO> getData();

    public static final Retrofit gitRetrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.6")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
