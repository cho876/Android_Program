package com.example.cho.haneum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Cho on 2017-08-25.
 */

public interface RetrofitService {
    @GET("/Json.php")
    Call<List<Users>> getUsers();

    @GET("/Json.php")
    Call<List<Users>> getUserId(
            @Query("userName") String userName,
            @Query("userEmail") String userEmail);

    @GET("/Json.php")
    Call<List<Users>> getUserPw(
            @Query("userEmail") String userEmail,
            @Query("userID") String userID);

    @GET("/curJson.php")
    Call<Data> getData();

    public static final Retrofit gitRetrofit = new Retrofit.Builder()
            .baseUrl("http://211.253.25.169")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
