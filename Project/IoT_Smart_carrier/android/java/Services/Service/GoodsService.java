package com.example.skcho.smartcarrier.Services.Service;

import android.util.Log;

import com.example.skcho.smartcarrier.Api.DeliverResponse;
import com.example.skcho.smartcarrier.Api.GoodsResponse;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.DeliveryEvent;
import com.example.skcho.smartcarrier.Services.Event.DeliveryListEvent;
import com.example.skcho.smartcarrier.Services.Event.GoodsEvent;
import com.example.skcho.smartcarrier.Services.Event.GoodsListEvent;
import com.example.skcho.smartcarrier.resources.GoodsResource;

import java.util.List;

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

public class GoodsService {

    private String TAG = "GoodsService";
    public static String SERVER_URL = "http://14.49.36.196";
    public static Retrofit retrofit;

    public GoodsService() {
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

    public void getGoodsById(String method, String userId){
        GoodsResource goodsResource = retrofit.create(GoodsResource.class);

        Call<List<GoodsResponse>> call = goodsResource.getGoodsById(method, userId);
        call.enqueue(new Callback<List<GoodsResponse>>() {
            @Override
            public void onResponse(Call<List<GoodsResponse>> call, Response<List<GoodsResponse>> response) {
                BusProvider.getInstance().post(new GoodsListEvent(response.body()));
                Log.e(TAG, "GET - Success "+response.toString());
            }

            @Override
            public void onFailure(Call<List<GoodsResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void deleteGoodsById(String method, String userId, String index){
        GoodsResource goodsResource = retrofit.create(GoodsResource.class);

        Call<GoodsResponse> call = goodsResource.deleteGoodsById(method, userId, index);
        call.enqueue(new Callback<GoodsResponse>() {
            @Override
            public void onResponse(Call<GoodsResponse> call, Response<GoodsResponse> response) {
                BusProvider.getInstance().post(new GoodsEvent(response.body()));
                Log.e(TAG, "DELETE - Success "+response.toString());
            }

            @Override
            public void onFailure(Call<GoodsResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void deliverGoodsById(String method, String userId){
        GoodsResource goodsResource = retrofit.create(GoodsResource.class);

        Call<DeliverResponse> call = goodsResource.deliverGoodsById(method, userId);
        call.enqueue(new Callback<DeliverResponse>() {
            @Override
            public void onResponse(Call<DeliverResponse> call, Response<DeliverResponse> response) {
                BusProvider.getInstance().post(new DeliveryEvent(response.body()));
                Log.e(TAG, "DELETE - Success "+response.toString());
            }

            @Override
            public void onFailure(Call<DeliverResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    // 모든 배송 리스트 조회 (관리자)
    public void allDeliveryList(String method){
        GoodsResource goodsResource = retrofit.create(GoodsResource.class);

        Call<List<DeliverResponse>> call = goodsResource.allDeliveryList(method);  // "/goods/allDelivery.php"
        call.enqueue(new Callback<List<DeliverResponse>>() {
            @Override
            public void onResponse(Call<List<DeliverResponse>> call, Response<List<DeliverResponse>> response) {
                BusProvider.getInstance().post(new DeliveryListEvent(response.body()));
                Log.e(TAG, "ALL - Success "+response.toString());
            }

            @Override
            public void onFailure(Call<List<DeliverResponse>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    // 배송 서비스 승락 관리 (관리자)
    public void serviceDeliverById(String method, String userId){
        GoodsResource goodsResource = retrofit.create(GoodsResource.class);

        Call<DeliverResponse> call = goodsResource.serviceDeliveryById(method, userId);  // "/goods/serviceDelivery.php"
        call.enqueue(new Callback<DeliverResponse>() {
            @Override
            public void onResponse(Call<DeliverResponse> call, Response<DeliverResponse> response) {
                BusProvider.getInstance().post(new DeliveryEvent(response.body()));
                Log.e(TAG, "ALL - Success "+response.toString());
            }

            @Override
            public void onFailure(Call<DeliverResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
