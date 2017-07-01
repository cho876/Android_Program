package com.example.cho.threadhandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
    Title: Handler와 Thread 사이 관계 이해

            - Thread는 병렬 처리 기능을 하므로 핸드폰 UI를 바꿀 수 없다.
                          이에 Handler 내에서 Thread를 통해 바꿀 UI를 대신 처리해줄 수 있음을 보여주는 예제
 */

public class MainActivity extends AppCompatActivity {

    private TextView mText;
    private TextView bText;
    private Button btn;
    int mainValue = 0;
    int backValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView) findViewById(R.id.main);
        bText = (TextView) findViewById(R.id.back);
        btn = (Button) findViewById(R.id.btn);

        BackRunnable runnable = new BackRunnable();
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainValue++;
                mText.setText("main Count = " + mainValue);
                bText.setText("back Count = " + backValue);
            }
        });
    }

    class BackRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                backValue++;
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                bText.setText("back Count = " + backValue);
        }
    };

}
