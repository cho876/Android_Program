package com.example.skcho.smartcarrier.Services.Service;

import android.util.Log;

import com.example.skcho.smartcarrier.Api.CouponResponse;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.CouponEvent;
import com.example.skcho.smartcarrier.Services.Event.CouponListEvent;
import com.example.skcho.smartcarrier.resources.CouponResource;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by skCho on 2018-04-03.
 */

public class CouponService {
    private String TAG = "CouponService";
    public static String SERVER_URL = "http://14.49.36.196";
    public static Retrofit retrofit;

    public CouponService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(SERVER_URL)
                .build();
    }

    public void getCouponById(String userId){
        CouponResource couponResource = retrofit.create(CouponResource.class);

        Call<List<CouponResponse>> call = couponResource.getCouponById("get", userId);
        call.enqueue(new Callback<List<CouponResponse>>() {
            @Override
            public void onResponse(Call<List<CouponResponse>> call, Response<List<CouponResponse>> response) {
                BusProvider.getInstance().post(new CouponListEvent(response.body()));
                Log.e(TAG, "GET - Success");
            }

            @Override
            public void onFailure(Call<List<CouponResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void useCouponById(String userId, String index){
        CouponResource couponResource = retrofit.create(CouponResource.class);

        Call<CouponResponse> call = couponResource.useCouponById("use", userId, index);
        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                BusProvider.getInstance().post(new CouponEvent(response.body()));
                Log.e(TAG, "GET - Success");
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void sendCoupon(String method, String value, String desc) {
        CouponResource couponResponce = retrofit.create(CouponResource.class);

        Call<CouponResponse> call = couponResponce.sendCoupon(method, value, desc);
        call.enqueue(new Callback<CouponResponse>() {
            @Override
            public void onResponse(Call<CouponResponse> call, Response<CouponResponse> response) {
                BusProvider.getInstance().post(new CouponEvent(response.body()));
                Log.e(TAG, "SEND - Success");
            }

            @Override
            public void onFailure(Call<CouponResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}