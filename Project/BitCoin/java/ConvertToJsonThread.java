package com.example.cho.bitcoin;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    private Handler handler;
    private String company;
    private String title;


    public ConvertToJsonThread(Context context, RegisterRequest registerRequest, Handler handler) {
        this.context = context;
        this.registerRequest = registerRequest;
        this.handler = handler;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void run() {
        try {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String sTemp = jsonResponse.getJSONObject(company).toString();
                        JSONObject jsonObject = new JSONObject(sTemp).getJSONObject(title);
                        Log.e("ConvertToJsonThread", title);
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = jsonObject;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Log.e("ConvertToJsonThread", "ERROR");
                    }
                }
            };
            registerRequest = new RegisterRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(registerRequest);
        } catch (Exception e) {
            Log.e("ConvertToJsonThread", "ERROR");
        }
    }
}
