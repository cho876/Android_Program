package com.example.cho.haneum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {    // "수조 설정" 화면

    private String sId, sTemp, sTurb, sLevel;
    private Button bLeft, bRight;
    private EditText ed_temp, ed_turb;     // 온도, 탁도 EditText
    private ProgressDialog dialog;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;
    private CustomButton customButton;
    private ValidCheck validCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);      // SharedPreference  Setting
        editor = pref.edit();
        initView();
    }

    public void initView() {
        ed_temp = (EditText) findViewById(R.id.setting_Temp);    // 온도 EditText
        ed_turb = (EditText) findViewById(R.id.setting_Turb);   // 탁도 EditText

        customButton = (CustomButton) findViewById(R.id.custom_setting_btn);
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        validCheck = new ValidCheck(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right:   // "확인" 버튼 클릭 시,
                writeToDB();  // 데이터 저장
                break;
            case R.id.button_left:  // "돌아가기" 화면
                this.finish();
                break;
        }
    }

    public void writeToDB() {         // DB 내, 저장 Func
        sId = pref.getString("Id", "");          // 기존에 저장된 ID 호출 및 저장 (Key 역할)
        sTemp = ed_temp.getText().toString() + ".00";
        sTurb = ed_turb.getText().toString() + ".00";

        dialog.setMessage("설정 값을 저장 중입니다...");
        dialog.show();
        if (validCheck.isWrited(sId, sTemp, sTurb)) {
            JoinDB joinDB = new JoinDB();
            joinDB.execute();
        } else
            dialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {           // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    //////////////////////////////////        JoinDB            ////////////////////////////////////

                          /*     AsyncTask를 통한 Threading 작업 class     */

    public class JoinDB extends AsyncTask<Void, Integer, Void> {
        private String data;

        @Override
        protected Void doInBackground(Void... unused) {
            String param = "u_id=" + sId + "&u_temp=" + sTemp + "&u_turb=" + sTurb + "";

            try {
                URL url = new URL(
                        "http://211.253.25.169/setting.php");
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
                if (data.equals("1"))
                    Log.e("RESULT", "Success");
                else
                    Log.e("RESULT", "Fail - " + data);
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
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SettingActivity.this);
            if (data.equals("1")) {
                Toast.makeText(SettingActivity.this, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                Intent go_home = new Intent(SettingActivity.this, LoginActivity.class);
                UtilCheck.UtilClose(go_home);
                startActivity(go_home);
            } else {
                alertBuilder
                        .setTitle("알림")
                        .setMessage("입력 가능 범위를 벗어났습니다.\n(0 ~ 99)")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeOptionsMenu();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
            dialog.dismiss();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
