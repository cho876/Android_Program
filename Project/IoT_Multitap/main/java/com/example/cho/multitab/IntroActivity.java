package com.example.cho.multitab;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tsengvn.typekit.TypekitContextWrapper;

public class IntroActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    /**
     * 4초 뒤 Main화면으로 전환
     */
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    /**
     * 4초 뒤, Runnable 실행
     */
    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(r, 4000);
    }

    /**
     * 화면 벗어날 시, handler에 예약해놓은 작업 취소
     */
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
