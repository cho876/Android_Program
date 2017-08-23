package com.example.cho.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Retrofit 기본 Setting Interface
 *
 * @author SungkwonCho
 */
public interface MomoService {

    @GET("/repos/square/retrofit/contributors")
    Call<List<Stores>> getStoreList();

    public static final Retrofit gitRetrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}