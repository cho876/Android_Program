package com.example.cho.retrofit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cho on 2017-08-22.
 * Retrofit 통신을 통해 받아온 데이터를 User의 사용에 맞게끔 Getter/Setter화 시킨 클래스
 *
 * @author SungkwonCho
 */
public class MomoConnect {

    /**
     * "비동기적" 방식으로 User가 원하는 Store 정보를 받기 위한
     * Callback 함수를 위한 Interface
     *
     * @param <T>
     */
    interface callBack<T> {
        void execute(T t);
    }

    //public void getUser

    /**
     * 동기적 방식으로 postId, id에 해당하는 User 정보
     * Callback 방식을 통한 반환
     *
     * @param postId
     * @param id
     * @param successCallback (callBackStores Interface)
     */
    public void getUserByAsyn(final int postId, final int id, final callBack<Users> successCallback) {
        MomoService momoService = MomoService.gitRetrofit.create(MomoService.class);
        Call<List<Users>> call = momoService.getUserInformatiobn(postId, id);
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> usersList = response.body();
                for (Users user : usersList) {
                    if (user.getPostId() == postId && user.getId() == id) {
                        Log.e("getUsersByAsyn", "찾음: userName - " + user.getName());
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
     * 동기적 방식으로 postId, id에 해당하는 User 정보 반환
     *
     * @param postId
     * @param id
     */
    public void getUserBySyn(final int postId, final int id) {
        new AsyncTask<Void, Void, Users>() {
            @Override
            protected Users doInBackground(Void... params) {
                MomoService momoService = MomoService.gitRetrofit.create(MomoService.class);
                Call<List<Users>> call = momoService.getUserInformatiobn(1, 1);
                try {
                    List<Users> usersList = call.execute().body();
                    for (Users user : usersList) {
                        if (user.getPostId() == postId && user.getId() == id) {
                            Log.e("getStoreBySyn", "찾음: userName - " + user.getName());
                            return user;
                        }
                    }
                } catch (IOException e) {
                    Log.e("onFailure", "getStoreBySyn Fail!");
                }
                return null;
            }
        }.execute();
    }
}