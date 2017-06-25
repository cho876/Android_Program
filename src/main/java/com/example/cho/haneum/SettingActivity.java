package com.example.cho.haneum;

import android.app.AlertDialog;
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

    final int[] MY_BUTTONS = {
            R.id.check_layout,
            R.id.return_layout
    };

    private String sId, sTemp, sTurb, sLevel;

    private EditText ed_temp = null;     // 온도 EditText
    private EditText ed_turb = null;    // 탁도 EditText
    private EditText ed_level = null;    // 물의 양 EditText
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }
        ed_temp = (EditText) findViewById(R.id.temperature);   // xml의 온도 EditText id
        ed_turb = (EditText) findViewById(R.id.turbidity);   // xml의 탁도 EditText id
        ed_level = (EditText) findViewById(R.id.level);   // xml의 물의 양 EditText id

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_layout:   // "확인" 버튼 클릭 시,
                saveDB();  // 데이터 저장
                break;
            case R.id.return_layout:  // "돌아가기" 화면
                this.finish();
                break;
        }
    }

    public void saveDB() {              // EditText의 입력 값 SharedPreferences에 저장
        sId = pref.getString("Id", "");
        sTemp = ed_temp.getText().toString();
        sTurb = ed_turb.getText().toString();
        sLevel = ed_level.getText().toString();

        if (UtilCheck.isChecked(sId) && UtilCheck.isChecked(sTemp) && UtilCheck.isChecked(sTurb) && UtilCheck.isChecked(sLevel)) {
            SetDB setDB = new SetDB();
            setDB.execute();
        }
    }

    public class SetDB extends AsyncTask<Void, Integer, Void> {
        private String data;

        @Override
        protected Void doInBackground(Void... unused) {
            String param = "u_id=" + sId + "&u_temp=" + sTemp + "&u_turb=" + sTurb + "&u_level=" + sLevel + "";

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
                editor.putString("Temp", sTemp);
                editor.putString("Turb", sTurb);
                editor.putString("Level", sLevel);
                editor.commit();
                Toast.makeText(SettingActivity.this, "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                Intent go_main = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(go_main);
                SettingActivity.this.finish();
            }
            else {
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
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
