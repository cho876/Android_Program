package com.example.skcho.smartcarrier;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ManualActivity extends AppCompatActivity implements View.OnTouchListener {

    int top_index = 0;
    int bottom_index = 0;
    int left_index = 0;
    int right_index = 0;
    ImageButton up;
    ImageButton down;
    ImageButton left;
    ImageButton right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Toast.makeText(getApplicationContext(), "수동 모드 실행 중", Toast.LENGTH_SHORT).show();
        init();
    }

    public void init()//변수 지정
    {
        up = (ImageButton) findViewById(R.id.up);
        up.setOnTouchListener(this);
        down = (ImageButton) findViewById(R.id.down);
        down.setOnTouchListener(this);
        left = (ImageButton) findViewById(R.id.left);
        left.setOnTouchListener(this);
        right = (ImageButton) findViewById(R.id.right);
        right.setOnTouchListener(this);

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.up:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    top_index = 1;
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    top_index = 0;
                break;

            case R.id.down:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    bottom_index = 1;
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    bottom_index = 0;
                break;

            case R.id.left:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    left_index = 1;
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    left_index = 0;
                break;

            case R.id.right:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    right_index = 1;
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    right_index = 0;
                break;
        }
        InsertKey insertkey = new InsertKey();
        insertkey.execute(top_index + "," + bottom_index + "," + left_index + "," + right_index);
        return false;
    }

    class InsertKey extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data;
            Log.e("PARAM:",params[0]);
            String param = "u_key=" + params[0] + "";    // 사용자가 기입한 값을 저장한 변수


            try {
                URL url = new URL(
                        "http://192.168.0.5/getManual.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터 값 전달*/
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                InputStream is = null;
                BufferedReader in = null;
                data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
                Log.e("ManualActivity", "SUCCESS - " + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
