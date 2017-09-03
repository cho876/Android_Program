package com.example.cho.haneum;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RetrofitService 클래스의 Retrofit 통신을 통한
 * DB 내, 데이터 Gettter / Setter 함수 모아둔 클래스
 */

public class RetrofitConnect {

    /**
     * Callback 인터페이스
     *
     * @param <T> (Callback을 통해 전달받을 자료형)
     */
    interface callBack<T> {
        void execute(T user);
    }

    /**
     * 비동기적 방식을 통한 UserID Getter
     *
     * @param userName        (유저 이름)
     * @param userEmail       (유저 이메일)
     * @param successCallback (Callback 함수)
     */
    public void getUserIdByAsync(final String userName, final String userEmail, final callBack<Users> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<List<Users>> call = retrofitService.getUserId(userName, userEmail);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> usersList = response.body();
                    for (Users user : usersList) {
                        if (user.getUserName().equals(userName) && user.getUserEmail().equals(userEmail)) {
                            successCallback.execute(user);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
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
    public void getUserPwByAsync(final String userEmail, final String userId, final callBack<Users> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<List<Users>> call = retrofitService.getUserPw(userEmail, userId);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.isSuccessful()) {
                    List<Users> usersList = response.body();
                    for (Users user : usersList) {
                        if (user.getUserEmail().equals(userEmail) && user.getUserID().equals(userId))
                            successCallback.execute(user);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.e("onFailure", "getUsersByAsyn Fail!");
            }
        });
    }

    /**
     * 비동기적 방식을 통한 Data(수조 상태를 담은 객체) Getter
     *
     * @param successCallback (Callback 함수)
     */
    public void getDataByAsync(final callBack<Data> successCallback) {
        RetrofitService retrofitService = RetrofitService.gitRetrofit.create(RetrofitService.class);
        Call<Data> call = retrofitService.getData();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Data dataTemp = response.body();
                    Log.e("SUCCESS", "성공 data - " + dataTemp.getCurTemp());
                    successCallback.execute(dataTemp);
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e("onFailure", "getUsersByAsyn Fail!");
            }
        });
    }
}