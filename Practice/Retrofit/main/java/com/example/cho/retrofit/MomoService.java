package com.example.cho.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Cho on 2017-08-22.
 */

public interface MomoService {

    /*  Select  (http://api.github.com/repos/{owner}/{repo}/contributors*/
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Users>> getUsers(             // => getUsers(String owner, String repo)
            @Path("owner") String owner
            , @Path("repo") String repo);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()) // Gson (json To Java Object)
            .build();
}
