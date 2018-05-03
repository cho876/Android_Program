package com.example.skcho.smartcarrier.resources;

import com.example.skcho.smartcarrier.Api.CouponResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by skCho on 2018-04-03.
 */

public interface CouponResource {
    @FormUrlEncoded
    @POST("/coupon/getCoupon.php")
    Call<List<CouponResponse>> getCouponById(
            @Field("method") String method,
            @Field("userId") String userId
    );

    @FormUrlEncoded
    @POST("/coupon/useCoupon.php")
    Call<CouponResponse> useCouponById(
            @Field("method") String method,
            @Field("userId") String userId,
            @Field("index") String index
    );

    @FormUrlEncoded
    @POST("/coupon/sendCoupon.php")
    Call<CouponResponse> sendCoupon(
            @Field("method") String method,
            @Field("value") String value,
            @Field("desc") String desc
    );
}