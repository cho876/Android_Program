package com.example.cho.multitab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PowerActivity extends AppCompatActivity {

    private ToggleButton toggle_consent1, toggle_consent2, toggle_consent3, toggle_consent4;
    private SwitchController switchController;     // Switch 제어 Class
    private SharedPreferenceGetSet sharedPreferenceGetSet;   //SharedPreference Getter / Setter
    private RegisterRequest registerRequest;      // Volley 서버 연동 Class
    private ActionBarCustom actionBarCustom;      // 상단 Custom ActionBar
    private JoinDbThread joinDbThread;            // 서버 연동 Thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        sharedPreferenceGetSet = new SharedPreferenceGetSet(this);
        initView();
        initActionBar();

        switchController = new SwitchController(this, toggle_consent1, toggle_consent2, toggle_consent3, toggle_consent4);
        switchController.checkSwitchStatus();
    }

    /**
     * 상단 Custom ActionBar 설정
     */
    private void initActionBar() {
        actionBarCustom = new ActionBarCustom(this);
        ActionBar actionBar = getSupportActionBar();
        actionBarCustom.setActionBar(actionBar);
    }


    /**
     * UI 초기화
     */
    private void initView() {
        toggle_consent1 = (ToggleButton) findViewById(R.id.toggle_consent_1);
        setToggleUI(toggle_consent1, sharedPreferenceGetSet.getPrefBoolean("consent_1_power"));
        toggle_consent2 = (ToggleButton) findViewById(R.id.toggle_consent_2);
        setToggleUI(toggle_consent2, sharedPreferenceGetSet.getPrefBoolean("consent_2_power"));
        toggle_consent3 = (ToggleButton) findViewById(R.id.toggle_consent_3);
        setToggleUI(toggle_consent3, sharedPreferenceGetSet.getPrefBoolean("consent_3_power"));
        toggle_consent4 = (ToggleButton) findViewById(R.id.toggle_consent_4);
        setToggleUI(toggle_consent4, sharedPreferenceGetSet.getPrefBoolean("consent_4_power"));
    }

    /**
     * 스위치 On / Off 상태에 따른 UI 설정
     *
     * @param toggleUI Toggle layout
     * @param isOn     SharedPreference에 저장된 On / Off 상태
     */
    private void setToggleUI(ToggleButton toggleUI, boolean isOn) {
        if (isOn) {
            toggleUI.setChecked(true);
            toggleUI.setBackgroundDrawable(getResources().getDrawable(R.drawable.poweron));
        } else {
            toggleUI.setChecked(false);
            toggleUI.setBackgroundDrawable(getResources().getDrawable(R.drawable.poweroff));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}