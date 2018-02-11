package com.example.skcho.smartcarrier;


import com.estimote.sdk.cloud.internal.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cho on 2017-12-28.
 */

public interface RetrofitService {
    @GET("/getCartInfoByJson.php")
    Call<CartDAO> getData();

    @GET("/Json.php")
    Call<List<UserDAO>> getUserId(
            @Query("userName") String userName,
            @Query("userEmail") String userEmail);

    @GET("/Json.php")
    Call<List<UserDAO>> getUserPw(
            @Query("userEmail") String userEmail,
            @Query("userID") String userID);

    public static final Retrofit gitRetrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.5")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
