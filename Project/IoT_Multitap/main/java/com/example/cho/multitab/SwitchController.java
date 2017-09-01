package com.example.cho.multitab;

import android.app.Activity;

import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


/**
 * Created by Cho on 2017-09-01.
 */

public class SwitchController {

    private Activity activity;
    private Switch switchFirst, switchSecond, switchThird, switchFourth;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    /**
     * Switch 제어 Class 초기화
     *
     * @param activity - PowerActivity
     * @param switches - 모든 Switch 객체
     */
    public SwitchController(Activity activity, Switch... switches) {
        this.activity = activity;

        pref = activity.getSharedPreferences("consentData", activity.MODE_PRIVATE);
        editor = pref.edit();

        switchFirst = switches[0];
        switchSecond = switches[1];
        switchThird = switches[2];
        switchFourth = switches[3];
    }

    /**
     * Switch 상태에 따른 SharedPreference에 상태 저장
     */
    public void checkSwitchStatus() {
        switchFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(activity, "1번 콘센트가 켜졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_1", isChecked);
                }
                if (isChecked == false) {
                    Toast.makeText(activity, "1번 콘센트가 꺼졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_1", isChecked);
                }
                editor.commit();
            }
        });

        switchSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(activity, "2번 콘센트가 켜졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_2", isChecked);
                }
                if (isChecked == false) {
                    Toast.makeText(activity, "2번 콘센트가 꺼졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_2", isChecked);
                }
                editor.commit();
            }
        });

        switchThird.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(activity, "3번 콘센트가 켜졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_3", isChecked);
                }
                if (isChecked == false) {
                    Toast.makeText(activity, "3번 콘센트가 꺼졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_3", isChecked);
                }
                editor.commit();
            }
        });

        switchFourth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(activity, "4번 콘센트가 켜졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_4", isChecked);
                }
                if (isChecked == false) {
                    Toast.makeText(activity, "4번 콘센트가 꺼졌습니다. ", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("consent_4", isChecked);
                }
                editor.commit();
            }
        });
    }
}
