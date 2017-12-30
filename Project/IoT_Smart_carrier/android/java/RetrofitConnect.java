package com.example.skcho.smartcarrier;


import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cho on 2017-12-17.
 */

public class RetrofitConnect {


    interface callBack<T> {
        void execute(T user);
    }


    public void getDataByAsync(final callBack<CartDAO> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<CartDAO> call = retrofitService.getData();
        call.enqueue(new Callback<CartDAO>() {
            @Override
            public void onResponse(Call<CartDAO> call, Response<CartDAO> response) {
                if (response.isSuccessful()) {
                    CartDAO data = response.body();
                    Log.e("SUCCESS", "Distance - " + data.getDistance());
                    successCallback.execute(data);
                }
            }

            @Override
            public void onFailure(Call<CartDAO> call, Throwable t) {
                Log.e("onFailure", "getUsersByAsyn Fail!");
            }
        });
    }
}

