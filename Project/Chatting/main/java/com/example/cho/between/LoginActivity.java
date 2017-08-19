package com.example.cho.between;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog dialog;
    private EditText ed_email, ed_pw;
    private String sEmail, sPw;
    private Button bLogin;
    private ImageButton bJoin, bFind_id, bFind_pw;
    private CheckBox cb_id;
    private boolean isChecked = true;
    private Check_Valid checkValid;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("userPref", MODE_PRIVATE);
        isChecked = pref.getBoolean("userCheck", false);
        editor = pref.edit();
        initView();
        FixedID(isChecked);
    }

    private void initView() {
        ed_email = (EditText) findViewById(R.id.login_email);
        ed_pw = (EditText) findViewById(R.id.login_pw);
        cb_id = (CheckBox) findViewById(R.id.login_chk);

        bJoin = (ImageButton) findViewById(R.id.login_join);
        bJoin.setOnClickListener(this);
        bFind_id = (ImageButton) findViewById(R.id.login_findId);
        bFind_id.setOnClickListener(this);
        bFind_pw = (ImageButton) findViewById(R.id.login_findPw);
        bFind_pw.setOnClickListener(this);

        bLogin = (Button) findViewById(R.id.login_login);
        bLogin.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려주세요...");
        checkValid = new Check_Valid(this);
    }

    /*  자동 로그인 기능 함수  */
    private void FixedID(boolean isChecked) {
        if (isChecked) {
            String email = pref.getString("userEmail", null);
            ed_email.setText(email);
            cb_id.setChecked(isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:                             // "로그인" Button Click
                sEmail = ed_email.getText().toString();
                sPw = ed_pw.getText().toString();
                if (checkValid.isValidEmail(sEmail) && checkValid.isValidPw(sPw)) {
                    dialog.show();
                    JoinDB joinDB = new JoinDB();
                    joinDB.execute();
                }
                break;
            case R.id.login_join:                              // "회원가입" Button Click
                Intent intent = new Intent(this, JoinActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(context));
    }

    public class JoinDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_id=" + sEmail + "&u_pw=" + sPw + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/prac_login.php");
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
                if (data.equals("Fail"))
                    Log.e("RESULT", "Fail - " + data);
                else
                    Log.e("RESULT", "Success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (data.equals("")) {                                       //  프로그램적 오류 발생 시,
                Toast.makeText(getApplicationContext(), "로그인 작업에 실패했습니다.", Toast.LENGTH_SHORT).show();
            } else if (data.equals("Fail"))                              //  회원 정보가 없을 시,
                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
            else {
                String[] array = data.split(",");
                Log.e("DATA", array[0] + ", " + array[1] + ", " + array[2]);
                editor.putString("userEmail", array[0]);
                editor.putString("userName", array[1]);
                editor.commit();

                Toast.makeText(getApplicationContext(), "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    /* Activity 종료 시, 자동 로그인 체크 여부 확인 및 상태 저장  */
    @Override
    public void onStop() {
        super.onStop();
        editor.putBoolean("userCheck", cb_id.isChecked());
        editor.commit();
    }
}