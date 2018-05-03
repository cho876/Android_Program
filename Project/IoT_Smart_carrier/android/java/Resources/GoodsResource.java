package com.example.skcho.smartcarrier.resources;

import com.example.skcho.smartcarrier.Api.CouponResponse;
import com.example.skcho.smartcarrier.Api.DeliverResponse;
import com.example.skcho.smartcarrier.Api.GoodsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by skCho on 2018-04-02.
 */

public interface GoodsResource {
    @FormUrlEncoded
    @POST("/goods/insertGoods.php")
    Call<List<GoodsResponse>> getGoodsById(
            @Field("method") String method,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("/goods/deleteGoods.php")
    Call<GoodsResponse> deleteGoodsById(
            @Field("method") String method,
            @Field("userId") String userId,
            @Field("index") String index
    );

    @FormUrlEncoded
    @POST("/goods/deliverGoods.php")
    Call<DeliverResponse> deliverGoodsById(
            @Field("method") String method,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("/goods/allDelivery.php")
    Call<List<DeliverResponse>> allDeliveryList(
            @Field("method") String method
    );

    @FormUrlEncoded
    @POST("/goods/serviceDelivery.php")
    Call<DeliverResponse> serviceDeliveryById(
            @Field("method") String method,
            @Field("userId") String userId
    );
}