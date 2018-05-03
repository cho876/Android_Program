package com.example.skcho.smartcarrier.Services.Service;

import android.util.Log;

import com.example.skcho.smartcarrier.Api.UserResponse;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.UserEvent;
import com.example.skcho.smartcarrier.resources.UserResource;

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

public class UserService {
    private String TAG = "UserService";
    public static String SERVER_URL = "http://14.49.36.196";
    public static Retrofit retrofit;

    public UserService() {
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

    public void logoutById(String method, String userId){
        UserResource userResource = retrofit.create(UserResource.class);

        Call<UserResponse> call = userResource.logoutUser(method, userId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                BusProvider.getInstance().post(new UserEvent(response.body()));
                Log.e(TAG, "Logout - Success");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Logout - Fail");

            }
        });
    }

    public void getUserById(String method, String userId, String password){
        UserResource userResource = retrofit.create(UserResource.class);

        Call<UserResponse> call = userResource.getUserById(method, userId, password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                BusProvider.getInstance().post(new UserEvent(response.body()));
                Log.e(TAG, "Login - Success");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Login - Fail");
            }
        });
    }

    public void setNewUser(String userId, String password, String name, String address, String email, String sex, String age){
        UserResource userResource = retrofit.create(UserResource.class);

        Call<UserResponse> call = userResource.newUser("join", userId, password, name, address, email, sex, age);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                BusProvider.getInstance().post(new UserEvent(response.body()));
                Log.e(TAG, "Join - Success");
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Join - Fail");

            }
        });
    }
}
