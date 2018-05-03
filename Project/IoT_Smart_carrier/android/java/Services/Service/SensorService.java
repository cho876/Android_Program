package com.example.skcho.smartcarrier.Services.Service;

import android.util.Log;

import com.example.skcho.smartcarrier.Api.SensorResponse;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.SensorEvent;
import com.example.skcho.smartcarrier.resources.SensorResource;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by skCho on 2018-04-02.
 */

public class SensorService {
    private String TAG = "SensorService";
    public static String SERVER_URL = "http://14.49.36.196";
    public static Retrofit retrofit;

    public SensorService() {
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

    public void getSensorValue(){
        SensorResource sensorResource = retrofit.create(SensorResource.class);

        Call<SensorResponse> call = sensorResource.getValue("get");
        call.enqueue(new Callback<SensorResponse>() {
            @Override
            public void onResponse(Call<SensorResponse> call, Response<SensorResponse> response) {
                BusProvider.getInstance().post(new SensorEvent(response.body()));
                Log.e(TAG, "SENSOR - Success");
            }

            @Override
            public void onFailure(Call<SensorResponse> call, Throwable t) {
                Log.e(TAG, "SENSOR - Fail");
            }
        });
    }
}
