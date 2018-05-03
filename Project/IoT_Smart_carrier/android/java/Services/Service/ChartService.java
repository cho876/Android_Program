package com.example.skcho.smartcarrier.Services.Service;

import android.util.Log;

import com.example.skcho.smartcarrier.Api.ChartResponse;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.ChartListEvent;
import com.example.skcho.smartcarrier.resources.ChartResponce;

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

public class ChartService {
    private String TAG = "ChartService";
    public static String SERVER_URL = "http://14.49.36.196";
    public static Retrofit retrofit;

    public ChartService() {
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

    public void getUserChartById(String userId){
        ChartResponce chartResponce = retrofit.create(ChartResponce.class);

        Call<List<ChartResponse>> call = chartResponce.getUserChartById("user", userId);
        call.enqueue(new Callback<List<ChartResponse>>() {
            @Override
            public void onResponse(Call<List<ChartResponse>> call, Response<List<ChartResponse>> response) {
                BusProvider.getInstance().post(new ChartListEvent(response.body()));
                Log.e(TAG, "USER - Success");
            }

            @Override
            public void onFailure(Call<List<ChartResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getAllUserBySex(){
        ChartResponce chartResponce = retrofit.create(ChartResponce.class);
        Call<List<ChartResponse>> call = chartResponce.getChartBySex("get");
        call.enqueue(new Callback<List<ChartResponse>>() {
            @Override
            public void onResponse(Call<List<ChartResponse>> call, Response<List<ChartResponse>> response) {
                BusProvider.getInstance().post(new ChartListEvent(response.body()));
                Log.e(TAG, "SEX - Success");
            }

            @Override
            public void onFailure(Call<List<ChartResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void getAllUserByAge(){
        ChartResponce chartResponce = retrofit.create(ChartResponce.class);
        Call<List<ChartResponse>> call = chartResponce.getChartByAge("get");
        call.enqueue(new Callback<List<ChartResponse>>() {
            @Override
            public void onResponse(Call<List<ChartResponse>> call, Response<List<ChartResponse>> response) {
                BusProvider.getInstance().post(new ChartListEvent(response.body()));
                Log.e(TAG, "AGE - Success");
            }

            @Override
            public void onFailure(Call<List<ChartResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}