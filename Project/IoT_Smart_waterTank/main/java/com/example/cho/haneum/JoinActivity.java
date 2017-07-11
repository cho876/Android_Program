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
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {  // "회원가입" 화면

    private Button bLeft, bRight;
    private String sName, sEmail, sId, sPw, sPw_chk;
    private EditText ed_name, ed_email, ed_id, ed_pw, ed_pwchk;
    private CustomButton customButton;
    private ProgressDialog dialog;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);        // SharedPreference  Setting
        editor = pref.edit();
        initView();
    }

    /*      View 초기화      */
    public void initView() {
        ed_name = (EditText) findViewById(R.id.join_Name);            // "이름" 기입 란
        ed_email = (EditText) findViewById(R.id.join_Email);          // "이메일" 기입 란
        ed_id = (EditText) findViewById(R.id.join_Id);                // "아이디" 기입 란
        ed_pw = (EditText) findViewById(R.id.join_Pw);                // "비밀번호" 기입 란
        ed_pwchk = (EditText) findViewById(R.id.join_Pwchk);          // "비밀번호 확인" 기입 란

        customButton = (CustomButton) findViewById(R.id.custom_join_btn);      // 돌아가기, 화인 버튼
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);

        dialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right:    // "확인" 화면
                writeToDB();
                break;
            case R.id.button_left:  // "돌아가기" 화면
                Intent go_login = new Intent(JoinActivity.this, LoginActivity.class);   // 회원가입 -> 로그인 창 이동
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
        }
    }

    /*      기입란 모두 작성 완료여부 확인   */
    public boolean isWrited(String... str) {
        for (String edit : str)
            if (!UtilCheck.isChecked(edit))
                return false;
        return true;
    }

    /*      이메일 형식 여부 확인    */
    private boolean isValidEmail(String sEmail) {
        if (!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
            return false;
        return true;
    }

    public void writeToDB() {      // DB 내, 저장 Func
        sName = ed_name.getText().toString();        // 이름 변환
        sEmail = ed_email.getText().toString();      // 이메일 변환
        sId = ed_id.getText().toString();            // 아이디 변환
        sPw = ed_pw.getText().toString();            // 비밀번호 변환
        sPw_chk = ed_pwchk.getText().toString();      // 비밀번호 확인 변환

        dialog.setMessage("등록 중입니다...");
        dialog.show();
        if (isWrited(sName, sEmail, sId, sPw, sPw_chk)) {                                            // 빈칸 공백 X,
            if (sPw.equals(sPw_chk)) {                                  // 비밀번호 && 비밀번호 확인
                if (!isValidEmail(sEmail)) { // 이메일 형식과 어긋날 시,
                    Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {                       // 이메일 형식에 맞을 시,
                    editor.putString("Id", sId);     // Input to SharedPreference (Key: Id, value: sId)
                    editor.commit();
                    JoinDB rdb = new JoinDB();      // Android -> DB 저장 (AsyncTask 실행)
                    rdb.execute();
                }
            }
            else {                                                   // 비밀번호 != 비밀번호 학인
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        } else {                                                                                     // 빈칸 공백 O,
            Toast.makeText(JoinActivity.this, "회원 정보를 모두 기입해주세요.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    //////////////////////////////////        JoinDB            ////////////////////////////////////

               /*            AsyncTask를 통한 Threading 작업 class           */

    class JoinDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {          // Background 내부 처리
            String param = "u_name=" + sName + "&u_email=" + sEmail + "&u_id=" + sId + "&u_pw=" + sPw + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/Join.php");
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
                    Log.e("RESULT", "Success - " + data);
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
        protected void onPostExecute(Void aVoid) {          // Background 작업 후,
            super.onPostExecute(aVoid);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(JoinActivity.this);     // 알람 팝업창 생성
            if (data.equals("1")) {       // DB 저장 완료 성공 시,
                Intent go_setting = new Intent(JoinActivity.this, SettingActivity.class);
                startActivity(go_setting);
            } else {                     // DB 저장 완료 실패 시,
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
            dialog.dismiss();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
}