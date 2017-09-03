package com.example.cho.multitab;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.w3c.dom.Text;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private ActionBarCustom actionBarCustom;
    private TimePicker timePicker;
    private ToggleButton toggle_consent1, toggle_consent2, toggle_consent3, toggle_consent4;
    private TextView text_consent1, text_consent2, text_consent3, text_consent4;
    private SwitchController switchController;
    private SharedPreferenceGetSet sharedPreferenceGetSet;
    private AlarmMaker alarmMaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        sharedPreferenceGetSet = new SharedPreferenceGetSet(this);
        initActionBar();
        initView();
        switchController = new SwitchController(this, toggle_consent1, toggle_consent2, toggle_consent3, toggle_consent4);
        switchController.checkSwitchStatus();
        alarmMaker = new AlarmMaker(this);
    }

    /**
     * UI 초기화
     */
    private void initView() {
        text_consent1 = (TextView) findViewById(R.id.text_consent1_reserve);
        setTextView(text_consent1, 1);
        text_consent2 = (TextView) findViewById(R.id.text_consent2_reserve);
        setTextView(text_consent2, 2);
        text_consent3 = (TextView) findViewById(R.id.text_consent3_reserve);
        setTextView(text_consent3, 3);
        text_consent4 = (TextView) findViewById(R.id.text_consent4_reserve);
        setTextView(text_consent4, 4);

        toggle_consent1 = (ToggleButton) findViewById(R.id.timer_toggle_consent_1);
        setToggleUI(toggle_consent1, sharedPreferenceGetSet.getPrefBoolean("consent_1_timer"));
        toggle_consent1.setOnClickListener(this);
        toggle_consent2 = (ToggleButton) findViewById(R.id.timer_toggle_consent_2);
        setToggleUI(toggle_consent2, sharedPreferenceGetSet.getPrefBoolean("consent_2_timer"));
        toggle_consent2.setOnClickListener(this);
        toggle_consent3 = (ToggleButton) findViewById(R.id.timer_toggle_consent_3);
        setToggleUI(toggle_consent3, sharedPreferenceGetSet.getPrefBoolean("consent_3_timer"));
        toggle_consent3.setOnClickListener(this);
        toggle_consent4 = (ToggleButton) findViewById(R.id.timer_toggle_consent_4);
        setToggleUI(toggle_consent4, sharedPreferenceGetSet.getPrefBoolean("consent_4_timer"));
        toggle_consent4.setOnClickListener(this);

        timePicker = (TimePicker) findViewById(R.id.timepicker);
    }

    private boolean isOn(ToggleButton button) {
        if (button.isChecked())
            return true;
        else
            return false;
    }

    /**
     * TimePicker를 통해 예약한 시/분 얻는 함수
     *
     * @param text  예약된 시간으로 바꿔줄 TextView
     * @param index 예약된 시간으로 바꿔줄 TextView의 콘센트 번호
     */
    private void getReserveTime(TextView text, int index) {
        String ap = "오전";
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();
        if (hour > 11) {
            ap = "오후";
        }
        alarmMaker.setAlarm(index, hour, min);
        sharedPreferenceGetSet.setPrefString("consent_" + index + "_timer_ap", ap);
        sharedPreferenceGetSet.setPrefInt("consent_" + index + "_timer_hour", hour);
        sharedPreferenceGetSet.setPrefInt("consent_" + index + "_timer_min", min);
        setTextView(text, index);
    }

    /**
     * 예약된 시간으로 TextView 교체
     *
     * @param textView 교체할 TextView
     * @param index    교체할 TextView 콘센트 번호
     */
    private void setTextView(TextView textView, int index) {
        String ap = sharedPreferenceGetSet.getPrefString("consent_" + index + "_timer_ap");
        int hour = sharedPreferenceGetSet.getPrefInt("consent_" + index + "_timer_hour");
        int min = sharedPreferenceGetSet.getPrefInt("consent_" + index + "_timer_min");
        textView.setText(ap + " " + hour + " : " + min);
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

    /**
     * 1번 콘센트 ~ 4번 콘센트까지 예약 설정 ToggleButton
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timer_toggle_consent_1:
                if (isOn(toggle_consent1)) {
                    getReserveTime(text_consent1, 1);
                    Toast.makeText(getApplicationContext(), "1번 콘센트 예약 완료", Toast.LENGTH_SHORT).show();
                }else
                    alarmMaker.cancelAlarm(1);
                break;
            case R.id.timer_toggle_consent_2:
                if (isOn(toggle_consent2)) {
                    getReserveTime(text_consent2, 2);
                    Toast.makeText(getApplicationContext(), "2번 콘센트 예약 완료", Toast.LENGTH_SHORT).show();
                }else
                    alarmMaker.cancelAlarm(2);
                break;
            case R.id.timer_toggle_consent_3:
                if (isOn(toggle_consent3)) {
                    getReserveTime(text_consent3, 3);
                    Toast.makeText(getApplicationContext(), "3번 콘센트 예약 완료", Toast.LENGTH_SHORT).show();
                }else
                    alarmMaker.cancelAlarm(3);
                break;
            case R.id.timer_toggle_consent_4:
                if (isOn(toggle_consent4)) {
                    getReserveTime(text_consent4, 4);
                    Toast.makeText(getApplicationContext(), "4번 콘센트 예약 완료", Toast.LENGTH_SHORT).show();
                }else
                    alarmMaker.cancelAlarm(4);
                break;
        }
    }

    /**
     * 상단 Custom ActionBar 설정
     */
    private void initActionBar() {
        actionBarCustom = new ActionBarCustom(this);
        ActionBar actionBar = getSupportActionBar();
        actionBarCustom.setActionBar(actionBar);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}