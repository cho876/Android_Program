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
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
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

/**
 * 로그인 Activity
 */

public class LoginActivity extends AppCompatActivity implements OnClickListener {  // "로그인" 화면

    final int[] MY_BUTTONS = {
            R.id.join_layout,
            R.id.findid_layout,
            R.id.findpw_layout
    };

    private EditText ed_id, ed_pw = null;
    private CheckBox cb_id = null;
    private String sId, sPw;
    private boolean saveId = true;
    private ProgressDialog dialog;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;
    private ValidCheck validCheck;
    private QuitHandler quitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();
        initView();

        quitHandler = new QuitHandler(this);

        for (int btnId : MY_BUTTONS) {
            ImageButton btn = (ImageButton) findViewById(btnId);
            btn.setOnClickListener(this);
        }
        Button btn = (Button) findViewById(R.id.login_layout);
        btn.setOnClickListener(this);
    }

    public void initView() {
        ed_id = (EditText) findViewById(R.id._login_id);
        ed_pw = (EditText) findViewById(R.id._login_pw);
        cb_id = (CheckBox) findViewById(R.id._login_chk);
        saveId = pref.getBoolean("check", false);              // 체크 박스 현재 상태 true/false 저장 (Boolean)
        FixedID(saveId);                                       //  아이디 자동 입력 기능

        dialog = new ProgressDialog(this);
        validCheck = new ValidCheck(this);
    }

    public void FixedID(boolean saveId) {            // 아이디 자동 입력 기능
        if (saveId) {   // Check 상태 true일 경우,
            String id = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력
            ed_id.setText(id);
            cb_id.setChecked(saveId);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_layout:    // "로그인" 버튼 클릭 시,
                writeToDB();
                break;
            case R.id.join_layout:    // "회원가입" 버튼 클릭 시,
                Intent go_join = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(go_join);
                break;
            case R.id.findid_layout:    // "회원정보 찾기" 버튼 클릭 시,
                Intent go_findid = new Intent(LoginActivity.this, FindIDActivity.class);
                startActivity(go_findid);
                break;
            case R.id.findpw_layout:
                Intent go_findpw = new Intent(LoginActivity.this, FindPWActivity.class);
                startActivity(go_findpw);
        }
    }

    public void writeToDB() {        // DB 내, 저장 Func
        sId = ed_id.getText().toString();
        sPw = ed_pw.getText().toString();

        dialog.setMessage("로그인 중입니다...");
        dialog.show();
        if (validCheck.isWrited(sId, sPw)) {        // 빈칸 공백 X,
            JoinDB joinDB = new JoinDB();
            joinDB.execute();
        } else
            dialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {        // "Back"버튼 두 번 누를 시, 프로그램 종료
        quitHandler.onBackPressed();
    }

    @Override
    public void onStop() {      // Activity 생명 주기 중, onStop() 호출 시,
        super.onStop();

        editor.putBoolean("check", cb_id.isChecked());    //Activity 종료 전, CheckBox 상태 값 저장
        editor.commit();
    }

    //////////////////////////////////        JoinDB            ////////////////////////////////////

                  /*            AsyncTask를 통한 로그인              */

    public class JoinDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {          // Background 내부 처리

            String param = "u_id=" + sId + "&u_pw=" + sPw + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/Login.php");
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
                Log.e("DATA", data.toString());
                if (data.equals("1"))
                    Log.e("RESULT", "Success" + data);
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
        protected void onPostExecute(Void aVoid) {          // Background 작업 후,
            super.onPostExecute(aVoid);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
            if (data.equals("1")) {             // 인증 완료 성공 시,
                editor.putString("Id", sId);
                editor.commit();
                Toast.makeText(LoginActivity.this, "회원인증 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent go_bt = new Intent(LoginActivity.this, MainActivity.class);
                UtilCheck.UtilClose(go_bt);
                startActivity(go_bt);
            } else if (data.equals("0")) {      // 회원 정보 다를 시,
                alertBuilder
                        .setTitle("알림")
                        .setMessage("회원정보가 틀렸습니다.")
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
    ///////////////////////////////////////////////////////////////////////////////////////////////
}