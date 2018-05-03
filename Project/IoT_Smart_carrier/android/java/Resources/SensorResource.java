package com.example.skcho.smartcarrier.resources;

import com.example.skcho.smartcarrier.Api.SensorResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SensorResource {
    @FormUrlEncoded
    @POST("/sensors/getValues.php")
    Call<SensorResponse> getValue(
            @Field("method") String method
    );
}