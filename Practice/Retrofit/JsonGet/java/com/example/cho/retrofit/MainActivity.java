package com.example.cho.retrofit;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Retrofit 통신을 통해 받아온 상점 정보를 바탕으로 UI 갱신 Activity
 *
 * @author SungkwonCho
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2;
    private MomoConnect momoConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView1 = (TextView) findViewById(R.id.test1);
        textView2 = (TextView) findViewById(R.id.test2);

        momoConnect = new MomoConnect();

        /**
         * 동기적 방식을 활용해 postId, id에 해당하는 UserName으로 TextView 변경
         */
        momoConnect.getUserByAsyn(1, 1, new MomoConnect.callBack<Users>() {
            @Override
            public void execute(Users user) {
                textView1.setText(user.getName());
            }
        });

        momoConnect.getUserByAsyn(1, 2, new MomoConnect.callBack<Users>() {
            @Override
            public void execute(Users user) {
                textView2.setText(user.getName());
            }
        });

        /**
         * 비동기적 방식을 활용해 postId, id에 해당하는 UserName 반환
         */
         momoConnect.getUserBySyn(1, 3);
         momoConnect.getUserBySyn(1, 2);
    }
}