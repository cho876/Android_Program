package com.example.skcho.smartcarrier.resources;


import com.example.skcho.smartcarrier.Api.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserResource {

    @FormUrlEncoded
    @POST("/users/getLogin.php")
    Call<UserResponse> getUserById(
            @Field("method") String method,
            @Field("userId") String userId,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/users/setMem.php")
    Call<UserResponse> newUser(
            @Field("method") String method,
            @Field("userId") String userId,
            @Field("password") String password,
            @Field("name") String name,
            @Field("address") String address,
            @Field("email") String email,
            @Field("sex") String sex,
            @Field("age") String age
    );

    @FormUrlEncoded
    @POST("/users/getLogin.php")
    Call<UserResponse> logoutUser(
            @Field("method") String method,
            @Field("userId") String userId
    );
}
