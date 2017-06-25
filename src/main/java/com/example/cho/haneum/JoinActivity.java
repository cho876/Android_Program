package com.example.cho.haneum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

    private String sModel, sName, sEmail, sId, sPw, sPw_chk;
    private EditText ed_model, ed_name, ed_email, ed_id, ed_pw, ed_pwchk;

    final int[] MY_BUTTONS = {
            R.id.check_layout,
            R.id.return_layout
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ed_model = (EditText) findViewById(R.id._join_model);
        ed_name = (EditText) findViewById(R.id._join_name);
        ed_id = (EditText) findViewById(R.id._join_id);
        ed_email = (EditText) findViewById(R.id._join_email);
        ed_pw = (EditText) findViewById(R.id._join_pw);
        ed_pwchk = (EditText) findViewById(R.id._join_pwchk);

        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_layout:    // "확인" 화면
                if (save_DB()) {
                    Intent go_setting = new Intent(JoinActivity.this, SettingActivity.class);
                    startActivity(go_setting);
                    break;
                } else {
                    Toast.makeText(this, "정보가 잘못 입력되었습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.return_layout:  // "돌아가기" 화면
                Intent go_login = new Intent(JoinActivity.this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
        }
    }

    public void Id_btn(View view) {           // 아이디 Check

    }

    public void Email_btn(View view) {       // E-mail Check

    }

    public boolean save_DB() {
        sModel = ed_model.getText().toString();
        sName = ed_name.getText().toString();
        sEmail = ed_email.getText().toString();
        sId = ed_id.getText().toString();
        sPw = ed_pw.getText().toString();
        sPw_chk = ed_pwchk.getText().toString();

        if (UtilCheck.isChecked(sModel) && UtilCheck.isChecked(sName) && UtilCheck.isChecked(sEmail) && UtilCheck.isChecked(sId)    // 빈칸 모두 기입할 시,
                && UtilCheck.isChecked(sPw) && UtilCheck.isChecked(sPw_chk)) {
            if (sPw.equals(sPw_chk)) {
                registerDB rdb = new registerDB();
                rdb.execute();
                return true;
            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {                                                                                                        // 빈칸이 존재할 시,
            Toast.makeText(JoinActivity.this, "회원 정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {    // 커스텀 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    //////////////////////////////////        registerDB            //////////////////////////////////////////
    class registerDB extends AsyncTask<Void, Integer, Void> {

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
                String data = "";

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
    }
}

