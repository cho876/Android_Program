package com.example.cho.haneum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {  // 메인 화면

    final int[] MY_BUTTONS = {
            R.id.register_layout,
            R.id.weather_layout,
            R.id.logout_layout,
            R.id.quit_layout
    };

    private TextView t_title, t_settemp, t_setturb, t_setlevel;
    private String sTemp, sTurb, sLevel, sId;
    private String title, data;
    private SharedPreferences pref = null;
    private QuitHandler quitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_title = (TextView) findViewById(R.id._main_title);
        t_settemp = (TextView) findViewById(R.id._main_settemp);
        t_setturb = (TextView) findViewById(R.id._main_setturb);
        t_setlevel = (TextView) findViewById(R.id._main_setlevel);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);

        quitHandler = new QuitHandler(this);
        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }
        displaySetDB displaySetDB = new displaySetDB();
        displaySetDB.execute();
    }

    class displaySetDB extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... unused) {
            title = pref.getString("Id", "");
            String param = "u_id=" + title + "";

            try {
                URL url = new URL(
                        "http://211.253.25.169/main.php");
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
                if (data.equals(null))
                    Log.e("RESULT", "Fail - " + data);
                else {
                    Log.e("RESULT", "Success - " + data);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String[] array = data.split(",");

            sTemp = array[0];
            sTurb = array[1];
            sLevel = array[2];

            t_title.setText(title + "'s\n어항 관리");
            t_settemp.setText(sTemp);
            t_setturb.setText(sTurb);
            t_setlevel.setText(sLevel);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_layout:   // "재등록" 버튼 클릭 시,
                Intent go_setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(go_setting);
                break;
            case R.id.weather_layout:   // "현재 날씨" 버튼 클릭 시,
                Intent go_weather = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(go_weather);
                break;
            case R.id.logout_layout:   // "로그 아웃" 버튼 클릭 시,
                Intent go_login = new Intent(MainActivity.this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
            case R.id.quit_layout:   // "종료" 버튼 클릭 시,
                quitHandler.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        quitHandler.onBackPressed();
    }    // "Back"버튼 두 번 누를 시, 프로그램 종료

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}