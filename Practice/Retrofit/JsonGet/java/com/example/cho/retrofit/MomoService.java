package com.example.cho.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Setting Interface
 *
 * @author SungkwonCho
 */
public interface MomoService {

    @GET("/comments")
    Call<List<Users>> getUserInformatiobn(
            @Query("postId") int postId
            , @Query("id") int id);

    public static final Retrofit gitRetrofit = new Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}