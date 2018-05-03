package com.example.skcho.smartcarrier.resources;

import com.example.skcho.smartcarrier.Api.ChartResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by skCho on 2018-04-03.
 */

public interface ChartResponce {
    @FormUrlEncoded
    @POST("/chart/getUserChart.php")
    Call<List<ChartResponse>> getUserChartById(
            @Field("method") String method,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("/chart/getDataBySex.php")
    Call<List<ChartResponse>> getChartBySex(
            @Field("method") String method
    );

    @FormUrlEncoded
    @POST("/chart/getDataByAge.php")
    Call<List<ChartResponse>> getChartByAge(
            @Field("method") String method
    );
}