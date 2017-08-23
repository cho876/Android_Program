package com.example.cho.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Retrofit 통신을 통해 받아온 상점 정보를 바탕으로 UI 갱신 Activity
 *
 * @author SungkwonCho
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private MomoConnect momoConnect;
    private Stores store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        store = new Stores();

        textView1 = (TextView) findViewById(R.id.test1);

        momoConnect = new MomoConnect();
        momoConnect.getStore("rjrjr", new MomoConnect.callBack<Stores>() {
            @Override
            public void execute(Stores store) {
                textView1.setText(store.getLogin());
            }
        });
    }
}