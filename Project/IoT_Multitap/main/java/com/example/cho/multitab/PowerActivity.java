package com.example.cho.multitab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PowerActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch switchFirst, switchSecond, switchThird, switchFourth;
    private Button bAllOn, bAllOff;
    private ImageButton Ib_Back;
    private SwitchController switchController;     // Switch 제어 Class
    private SharedPreferences pref;
    private RegisterRequest registerRequest;      // Volley 서버 연동 Class
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);
        pref = getSharedPreferences("consentData", MODE_PRIVATE);
        initTitlebar();
        initView();
        initThread();
        switchController = new SwitchController(this, switchFirst, switchSecond, switchThird, switchFourth);
        switchController.checkSwitchStatus();
        thread.start();
    }

    /**
     * 상단 Custom Actionbar 설정
     */
    private void initTitlebar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View customView = LayoutInflater.from(this).inflate(R.layout.custom_bar, null, false);
        actionBar.setCustomView(customView);

        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams
                (ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(customView, params);

    }

    /**
     * Thread 초기화
     * Volley를 통한 서버 연동
     * Response - DB로부터 성공적으로 저장되었을 시, JSON 형태로 "success":"true"를 받아옴
     */
    private void initThread() {
        thread = new Thread(new Runnable() {
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
                                        Log.e("PowerActivity", "성공적으로 DB 저장");
                                    else
                                        Log.e("PowerActivity", "DB 저장 실패");
                                } catch (Exception e) {
                                    Log.e("PowerActivity", "ERROR");
                                }
                            }
                        };
                        registerRequest = new RegisterRequest(pref.getBoolean("consent_1", false), pref.getBoolean("consent_2", false),
                                pref.getBoolean("consent_3", false), pref.getBoolean("consent_4", false), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(PowerActivity.this);
                        queue.add(registerRequest);
                        Thread.sleep(2000);
                    }
                } catch (Exception e) {
                    Log.e("PowerActivity", "Thread ERROR");
                }
            }
        });
        thread.setDaemon(true);
    }

    /**
     * UI 초기화
     */
    private void initView() {
        bAllOn = (Button) findViewById(R.id.btn_all_on);
        bAllOn.setOnClickListener(this);
        bAllOff = (Button) findViewById(R.id.btn_all_off);
        bAllOff.setOnClickListener(this);
        Ib_Back = (ImageButton) findViewById(R.id.imgbtn_back);
        Ib_Back.setOnClickListener(this);

        switchFirst = (Switch) findViewById(R.id.switch_consent_1);
        setSwitchUI(switchFirst, pref.getBoolean("consent_1", false));
        switchSecond = (Switch) findViewById(R.id.switch_consent_2);
        setSwitchUI(switchSecond, pref.getBoolean("consent_2", false));
        switchThird = (Switch) findViewById(R.id.switch_consent_3);
        setSwitchUI(switchThird, pref.getBoolean("consent_3", false));
        switchFourth = (Switch) findViewById(R.id.switch_consent_4);
        setSwitchUI(switchFourth, pref.getBoolean("consent_4", false));
    }

    /**
     * 스위치 On / Off 상태에 따른 UI 설정
     *
     * @param switchUI Switch layout
     * @param isOn     SharedPreference에 저장된 On / Off 상태
     */
    private void setSwitchUI(Switch switchUI, boolean isOn) {
        if (isOn)
            switchUI.setChecked(true);
        else
            switchUI.setChecked(false);
    }

    /**
     * 버튼 종류:
     *  - 모든 전원 On
     *  - 모든 전원 Off
     *  - 뒤로 가기
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_all_on:
                setConsentAllPowerOn();
                break;
            case R.id.btn_all_off:
                setConsentAllPowerOff();
                break;
            case R.id.imgbtn_back:
                this.finish();
                break;
        }
    }

    /**
     * 모든 콘센트 전원 On
     */
    private void setConsentAllPowerOn() {
        switchFirst.setChecked(true);
        switchSecond.setChecked(true);
        switchThird.setChecked(true);
        switchFourth.setChecked(true);
    }

    /**
     * 모든 콘센트 전원 Off
     */
    private void setConsentAllPowerOff() {
        switchFirst.setChecked(false);
        switchSecond.setChecked(false);
        switchThird.setChecked(false);
        switchFourth.setChecked(false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}