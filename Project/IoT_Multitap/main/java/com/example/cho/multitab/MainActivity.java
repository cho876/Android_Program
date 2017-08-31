package com.example.cho.multitab;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bPower, bTimer, bWhat, bExit;
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bPower = (Button) findViewById(R.id.btn_power);
        bPower.setOnClickListener(this);
        bTimer = (Button) findViewById(R.id.btn_timer);
        bTimer.setOnClickListener(this);
        bWhat = (Button) findViewById(R.id.btn1);
        bWhat.setOnClickListener(this);
        bExit = (Button) findViewById(R.id.btn_exit);
        bExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_power:
                Intent intent = new Intent(this, PowerActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_timer:
                Toast.makeText(getApplicationContext(), "타이머 설정", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn1:
                Toast.makeText(getApplicationContext(), "서비스 준비 중...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_exit:
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
