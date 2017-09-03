package com.example.cho.multitab;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ButtonCustom custombtn_power, custombtn_timer, custombtn_what, custombtn_exit;
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * UI 초기화
     */
    private void initView() {
        custombtn_power = (ButtonCustom) findViewById(R.id.custom_button_power);
        custombtn_power.setOnClickListener(this);
        custombtn_timer = (ButtonCustom) findViewById(R.id.custom_button_timer);
        custombtn_timer.setOnClickListener(this);
        custombtn_what = (ButtonCustom) findViewById(R.id.custom_button_what);
        custombtn_what.setOnClickListener(this);
        custombtn_exit = (ButtonCustom) findViewById(R.id.custom_button_exit);
        custombtn_exit.setOnClickListener(this);
    }

    /**
     * 버튼 종류:
     * - On/Off (콘센트 제어)
     * - 타이머 설정
     * - 준비 중
     * - 나가기
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.custom_button_power:
                Intent go_power = new Intent(this, PowerActivity.class);
                startActivity(go_power);
                break;

            case R.id.custom_button_timer:
                Intent go_timer = new Intent(this, TimerActivity.class);
                startActivity(go_timer);
                break;

            case R.id.custom_button_what:
                Toast.makeText(getApplicationContext(), "서비스 준비 중...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.custom_button_exit:
                onBackPressed();
                break;
        }
    }

    /**
     * 뒤로 가기 버튼 두번 연속 누를 시,  App 종료
     */
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(getApplicationContext(), "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(this);
            toast.cancel();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
