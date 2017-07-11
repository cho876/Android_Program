package com.example.cho.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
    TItle: Service를 활용한 상단 notification 호출 및 Toast 호출

 */
public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.bStart);          // Service Start Btn
        btnStop = (Button)findViewById(R.id.bStop);            // Service Stop Btn

        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MyService.class);      // Service Start Btn 누를 시, Service start
                startService(intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, MyService.class);     // Service Stop Btn 누를 시, Service stop
                stopService(intent);
            }
        });
    }
}
