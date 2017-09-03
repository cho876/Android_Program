package com.example.cho.multitab;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * JoinDbThread (Volley를 활용한 서버 연동 Thread)
 * Response - DB로부터 성공적으로 저장되었을 시, JSON 형태로 "success":"true"를 받아옴
 */

public class JoinDbThread extends Thread {

    private SharedPreferenceGetSet sharedPreferenceGetSet;
    private Context context;
    private RegisterRequest registerRequest;
    private String className;

    public JoinDbThread(Context context, RegisterRequest registerRequest) {
        this.context = context;
        className = context.getClass().getSimpleName().toLowerCase().substring(0,5);
        sharedPreferenceGetSet = new SharedPreferenceGetSet(context);
        this.registerRequest = registerRequest;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success)
                                Log.e("JoinDbThread", "성공적으로 DB 저장");
                            else
                                Log.e("JoinDbThread", "DB 저장 실패");
                        } catch (Exception e) {
                            Log.e("JoinDbThread", "ERROR");
                        }
                    }
                };
                registerRequest = new RegisterRequest(sharedPreferenceGetSet.getPrefBoolean("consent_1_"+className),
                        sharedPreferenceGetSet.getPrefBoolean("consent_2_"+className),
                        sharedPreferenceGetSet.getPrefBoolean("consent_3_"+className),
                        sharedPreferenceGetSet.getPrefBoolean("consent_4_"+className),
                        responseListener);

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(registerRequest);
                Log.e("DATA",sharedPreferenceGetSet.getPrefBoolean("consent_1_"+className)+", "+
                        sharedPreferenceGetSet.getPrefBoolean("consent_2_"+className)+", "+
                        sharedPreferenceGetSet.getPrefBoolean("consent_3_"+className)+", "+
                        sharedPreferenceGetSet.getPrefBoolean("consent_4_"+className));
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            Log.e("JoinDbThread", "Thread ERROR");
        }
    }
}
