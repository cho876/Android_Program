package com.example.skcho.smartcarrier;


import android.util.Log;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cho on 2017-01-07
 *
 *  Asynchronous data reception via Retrofit communication.
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

    /**
     * 비동기적 방식을 통한 UserID Getter
     *
     * @param userName        (유저 이름)
     * @param userEmail       (유저 이메일)
     * @param successCallback (Callback 함수)
     */
    public void getUserIdByAsync(final String userName, final String userEmail, final callBack<UserDAO> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<List<UserDAO>> call = retrofitService.getUserId(userName, userEmail);
        call.enqueue(new Callback<List<UserDAO>>() {
            @Override
            public void onResponse(Call<List<UserDAO>> call, Response<List<UserDAO>> response) {
                if (response.isSuccessful()) {
                    List<UserDAO> usersList = response.body();
                    for (UserDAO user : usersList) {
                        if (user.getUserName().equals(userName) && user.getUserEmail().equals(userEmail)) {
                            successCallback.execute(user);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserDAO>> call, Throwable t) {
                Log.e("onFailure", "getUsersByAsyn Fail!");
            }
        });
    }

    /**
     * 비동기적 방식을 통한 UserPw Getter
     *
     * @param userEmail       (유저 이메일)
     * @param userId          (유저 아이디)
     * @param successCallback (Callback 함수)
     */
    public void getUserPwByAsync(final String userEmail, final String userId, final callBack<UserDAO> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<List<UserDAO>> call  = retrofitService.getUserPw(userEmail, userId);
        call.enqueue(new Callback<List<UserDAO>>() {
            @Override
            public void onResponse(Call<List<UserDAO>> call, Response<List<UserDAO>> response) {
                if (response.isSuccessful()) {
                    List<UserDAO> usersList = response.body();
                    for (UserDAO user : usersList) {
                        if (user.getUserEmail().equals(userEmail) && user.getUserID().equals(userId))
                            successCallback.execute(user);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserDAO>> call, Throwable t) {
                Log.e("onFailure", "getUsersByAsyn Fail!");
            }
        });
    }
}

