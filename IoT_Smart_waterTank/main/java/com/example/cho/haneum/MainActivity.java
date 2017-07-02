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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {  // 메인 화면

    final int[] MY_BUTTONS = {
            R.id.register_layout,
            R.id.logout_layout,
            R.id.quit_layout
    };

    private TextView t_title, tv_temp, tv_turb, tv_level, tv_heat, tv_in, tv_out;   // 핸드폰 UI View Textview
    private String sTemp, sTurb, sLevel, sHeat, sIn, sOut;                    // 현재 온도, 탁도 ,수위, 히터, 급수, 배수 센서 상태

    private String setTemp, setTurb;        // 설정 온도 및 탁도 값
    private String title;                  // title: 아이디
    private SharedPreferences pref = null;
    private QuitHandler quitHandler;
    private Thread th;
    private boolean isChecked = true;
    private CustomTitle customTitle;
    private CustomTable customTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        customTitle = (CustomTitle) findViewById(R.id.custom_main_title);
        t_title = customTitle.getTitle();

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        title = pref.getString("Id", "");
        t_title.setText(title + "'s\n어항 관리");
        t_title.setTextSize(20);

        customTable = (CustomTable) findViewById(R.id.custom_main_temp);         // 수온 수치 TextViw
        tv_temp = customTable.getTv_lcontents();
        tv_heat = customTable.getTv_rcontents();

        customTable = (CustomTable) findViewById(R.id.custom_main_turb);        // 탁도 수치 TextView
        tv_turb = customTable.getTv_lcontents();
        tv_out = customTable.getTv_rcontents();

        customTable = (CustomTable) findViewById(R.id.custom_main_level);        // 탁도 수치 TextView
        tv_level = customTable.getTv_lcontents();
        tv_in = customTable.getTv_rcontents();


        quitHandler = new QuitHandler(this);
        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }

        displaySetDB displaySetDB = new displaySetDB();
        displaySetDB.execute();

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isChecked) {
                    Find_DB find_db = new Find_DB();
                    find_db.execute();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }

    class displaySetDB extends AsyncTask<Void, Integer, Void> {
        String data;
        @Override
        protected Void doInBackground(Void... unused) {
            String param = "u_id=" + title + "";

            try {
                URL url = new URL(
                        "http://211.253.25.169/main.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

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

            setTemp = sTemp + " / ";
            setTurb = sTurb + " / ";
            tv_temp.setText(setTemp);             // 설정 값으로 온도 TextView 수정
            tv_turb.setText(setTurb);             // 설정 값으로 탁도 TextView 수정
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_layout:   // "재등록" 버튼 클릭 시,
                Intent go_setting = new Intent(MainActivity.this, SettingActivity.class);
                UtilCheck.UtilClose(go_setting);
                startActivity(go_setting);
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

    public class Find_DB extends AsyncTask<Void, Integer, Void> {         // AsyncTask를 통한 서버 통신
        String data;
        String sId = pref.getString("Id", "");

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_id=" + sId + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/display.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

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
                if (data.equals(""))                  // php를 통해 값이 아무 것도 오지 않을 경우
                    Log.e("RESULT", "Fail - " + data);
                else {                       // 아이디를 불러올 경우
                    Log.e("RESULT", "Success");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        public void onOffSet(TextView tv, String value) {          // On/Off 상태 UI 변경 함수
            if (value.equals("0"))
                tv.setText("비동작 중");
            else
                tv.setText("동작 중");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String[] array = data.split(",");         // 현재 온도, 탁도 값 및 수위, 히터, 급수, 배수 제어상태 받아옴
            sTemp = array[0];
            sTurb = array[1];
            sLevel = array[2];
            sHeat = array[3];
            sIn = array[4];
            sOut = array[5];

            tv_temp.setText(setTemp + sTemp);       // 설정 온도 + 현재 온도 값으로 온도 TextView Set
            tv_turb.setText(setTurb + sTurb);       // 설정 탁도 + 현재 탁도 값으로 탁도 TextView Set
            onOffSet(tv_level, sLevel);        // 현재 수위 제어 상태 처리 (0: 비동작 / else: 동작)
            onOffSet(tv_heat, sHeat);      // 현재 히터 제어 상태 처리 (0: 비동작 / else: 동작)
            onOffSet(tv_in, sIn);      // 현재 급수 제어 상태 처리 (0: 비동작 / else: 동작)
            onOffSet(tv_out, sOut);      // 현재 배수 제어 상태 처리 (0: 비동작 / else: 동작)
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

    @Override
    public void onDestroy() {         // 액티비티 사라질 시, thread 정지
        super.onDestroy();
        isChecked = false;
    }
}