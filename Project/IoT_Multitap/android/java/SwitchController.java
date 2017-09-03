package com.example.cho.multitab;

import android.app.Activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * Created by Cho on 2017-09-01.
 */

public class SwitchController {

    private Activity activity;
    private ToggleButton toggleFirst, toggleSecond, toggleThird, toggleFourth;
    private SharedPreferenceGetSet sharedPreferenceGetSet;

    /**
     * Switch 제어 Class 초기화
     *
     * @param activity      - PowerActivity
     * @param toggleButtons - 모든 Switch 객체
     */
    public SwitchController(Activity activity, ToggleButton... toggleButtons) {
        this.activity = activity;
        sharedPreferenceGetSet = new SharedPreferenceGetSet(activity);

        toggleFirst = toggleButtons[0];
        toggleSecond = toggleButtons[1];
        toggleThird = toggleButtons[2];
        toggleFourth = toggleButtons[3];
    }

    /**
     * Switch 상태에 따른 ToggleButton 동작
     */
    public void checkSwitchStatus() {
        toggleFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeDrawable(toggleFirst);
                saveSharedPrefence(activity, 1, isChecked);
            }
        });

        toggleSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeDrawable(toggleSecond);
                saveSharedPrefence(activity, 2, isChecked);
            }
        });

        toggleThird.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeDrawable(toggleThird);
                saveSharedPrefence(activity, 3, isChecked);
            }
        });

        toggleFourth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeDrawable(toggleFourth);
                saveSharedPrefence(activity, 1, isChecked);
            }
        });
    }

    /**
     * ToggleButton의 작동 여부에 따른 Background 변경
     *
     * @param button 작동 여부 확인 ToggleButton
     */
    private void changeDrawable(ToggleButton button) {
        if (button.isChecked()) {
            button.setBackgroundDrawable(
                    activity.getResources().getDrawable(R.drawable.poweron));
        } else {
            button.setBackgroundDrawable(
                    activity.getResources().getDrawable(R.drawable.poweroff));
        }
    }

    /**
     * SharedPreference 내, 저장
     *
     * @param activity  해당 Activity
     * @param index     해당 콘센트 번호
     * @param isChecked 해당 ToggleButton의 작동 여부
     */
    private void saveSharedPrefence(Activity activity, int index, boolean isChecked) {
        if (activity.getClass().getSimpleName().equals("PowerActivity")) {
            sharedPreferenceGetSet.setPrefBoolean("consent_" + index + "_power", isChecked);
        } else if (activity.getClass().getSimpleName().equals("TimerActivity")) {
            sharedPreferenceGetSet.setPrefBoolean("consent_" + index + "_timer", isChecked);
        }
    }
}
