package com.example.cho.retrofit;

import android.util.Log;

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

    /**
     * shopName(상점이름)에 해당하는 정보를 찾아낸 후,
     * callBackStores Interface의 execute 함수를 통해 해당 Store 정보 반환
     *
     * @param shopName        (상점 이름)
     * @param successCallback (callBackStores Interface)
     */
    public void getStore(final String shopName, final callBack<Stores> successCallback) {
        MomoService momoService = MomoService.gitRetrofit.create(MomoService.class);
        Call<List<Stores>> call = momoService.getStoreList();
        call.enqueue(new Callback<List<Stores>>() {
            @Override
            public void onResponse(Call<List<Stores>> call, Response<List<Stores>> response) {
                List<Stores> storesList = response.body();

                for (Stores store : storesList) {
                    if (store.getLogin().equals(shopName)) {
                        successCallback.execute(store);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Stores>> call, Throwable t) {
                Log.e("onFailure", "getStore Fail!");
            }
        });
    }
}