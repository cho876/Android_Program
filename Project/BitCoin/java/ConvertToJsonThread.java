package com.example.cho.bitcoin;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Cho on 2017-09-06.
 */

/**
 * API로부터 Json 데이터를 불러오고 JSonObject로 파싱하는 Class
 */
public class ConvertToJsonThread extends Thread {
    private JSONObject jsonObject;
    private RegisterRequest registerRequest;
    private Context context;

    public ConvertToJsonThread(Context context, RegisterRequest registerRequest) {
        this.context = context;
        this.registerRequest = registerRequest;
    }

    @Override
    public void run() {
        try {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("JSONObject", response);
                        //JSONObject jsonResponse = new JSONObject(response);
                    } catch (Exception e) {
                        Log.e("ConvertToJsonThread", "ERROR");
                    }
                }
            };
            registerRequest = new RegisterRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(registerRequest);
        }catch (Exception e){
            Log.e("ConvertToJsonThread", "ERROR");
        }
    }
}
