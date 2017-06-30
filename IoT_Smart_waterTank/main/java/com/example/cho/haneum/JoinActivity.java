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

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {  // "회원가입" 화면

    private Button bLeft, bRight;
    private String sName, sEmail, sId, sPw, sPw_chk;
    private EditText ed_name, ed_email, ed_id, ed_pw, ed_pwchk;
    private CustomEdit customEdit;
    private CustomButton customButton;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        customEdit = (CustomEdit) findViewById(R.id.custom_join_Name);          // 이름 기입 란
        ed_name = customEdit.getEditText();

        customEdit = (CustomEdit) findViewById(R.id.custom_join_Email);          // 이메일 기입 란
        ed_email = customEdit.getEditText();

        customEdit = (CustomEdit) findViewById(R.id.custom_join_Id);            // 아이디 기입 란
        ed_id = customEdit.getEditText();

        customEdit = (CustomEdit) findViewById(R.id.custom_join_Pw);           // 비밀번호 기입 란
        ed_pw = customEdit.getEditText();

        customEdit = (CustomEdit) findViewById(R.id.custom_join_Pwchk);        // 비밀번호 확인 기입 란
        ed_pwchk = customEdit.getEditText();

        customButton = (CustomButton) findViewById(R.id.custom_join_btn);      // 돌아가기, 화인 버튼
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right:    // "확인" 화면
                save_DB();
                break;
            case R.id.button_left:  // "돌아가기" 화면
                Intent go_login = new Intent(JoinActivity.this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
        }
    }

    public void save_DB() {
        sName = ed_name.getText().toString();
        sEmail = ed_email.getText().toString();
        sId = ed_id.getText().toString();
        sPw = ed_pw.getText().toString();
        sPw_chk = ed_pwchk.getText().toString();

        if (UtilCheck.isChecked(sName) && UtilCheck.isChecked(sEmail) && UtilCheck.isChecked(sId)    // 빈칸 모두 기입할 시,
                && UtilCheck.isChecked(sPw) && UtilCheck.isChecked(sPw_chk)) {
            if (sPw.equals(sPw_chk)) {
                editor.putString("Id", sId);
                editor.commit();
                registerDB rdb = new registerDB();
                rdb.execute();
            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {                                                                                                        // 빈칸이 존재할 시,
            Toast.makeText(JoinActivity.this, "회원 정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {    // 커스텀 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    //////////////////////////////////        registerDB            //////////////////////////////////////////
    class registerDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {
            String param = "u_name=" + sName + "&u_email=" + sEmail + "&u_id=" + sId + "&u_pw=" + sPw + "";

            try {
                URL url = new URL(
                        "http://211.253.25.169/join.php");
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
                if (data.equals("1")) {
                    Log.e("RESULT", "Success");
                } else
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
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(JoinActivity.this);
            if (data.equals("1")) {
                Intent go_setting = new Intent(JoinActivity.this, SettingActivity.class);
                startActivity(go_setting);
            } else {
                alertBuilder
                        .setTitle("알림")
                        .setMessage("아이디가 중복됩니다.")
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
}