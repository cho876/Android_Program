package com.example.cho.haneum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class FindidActivity extends AppCompatActivity implements View.OnClickListener {  // "아이디 찾기" 화면

    private Button bLeft, bRight, bSearch;
    private EditText ed_name, ed_email;    // 이름, 이메일 기입란
    private String sName, sEmail;         // EditText -> String 변환 (이름, 이메일)
    private TextView text_id;             // 아이디 찾기 결과 변수
    private CustomButton customButton;
    private ValidCheck validCheck;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);
        initView();
    }

    /*      View 초기화      */
    public void initView() {
        ed_name = (EditText) findViewById(R.id.findId_Name);                           // 이름 기입 란
        ed_email = (EditText) findViewById(R.id.findId_Email);                          // 이메일 기입 란

        text_id = (TextView) findViewById(R.id._findid_search);                 // 검색 결과 란
        bSearch = (Button) findViewById(R.id.searchId_btn);                   // 아이디 찾기 버튼
        bSearch.setOnClickListener(this);

        customButton = (CustomButton) findViewById(R.id.custom_findId_btn);      // 뒤로가기, 비밀번호 찾기 버튼
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
            case R.id.searchId_btn:  // "아이디 찾기" 버튼 클릭 시,
                writeToDB();
                break;
            case R.id.button_right:  // "비밀번호 찾기" 버튼 클릭 시,
                Intent go_pw = new Intent(FindidActivity.this, FindpwActivity.class);
                startActivity(go_pw);
                break;
            case R.id.button_left:  // "홈으로" 버튼 클릭 시,
                Intent go_login = new Intent(this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);   // 이전 모든 Activity 종료
                startActivity(go_login);
                break;
        }
    }

    public void writeToDB() {           // DB 내, 저장 Func
        sName = ed_name.getText().toString();     // EditText -> String으로 변환
        sEmail = ed_email.getText().toString();

        dialog.setMessage("아이디를 찾는 중입니다...");
        dialog.show();
        if (validCheck.isWrited(sName, sEmail)) {       // 두 EditText 모두 기입할 시,
            Log.e("goTOjoin", "goJoin");
            JoinDB joinDB = new JoinDB();
            joinDB.execute();
        }else
            dialog.dismiss();
    }

    //////////////////////////////////        JoinDB            ////////////////////////////////////

                  /*            AsyncTask를 통한 Threading 작업 class                */

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public class JoinDB extends AsyncTask<Void, Integer, Void> {         // AsyncTask를 통한 서버 통신
        String data;

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_name=" + sName + "&u_email=" + sEmail + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/findID.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                Log.e("COnnET", "connect1");
                conn.connect();
                Log.e("COnnET", "connect2");
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
                Log.e("DATA", data);
                if (data.equals(""))                  // php를 통해 값이 아무 것도 오지 않을 경우
                    Log.e("RESULT", "Fail - " + data);
                else                                // 아이디를 불러올 경우
                    Log.e("RESULT", "Success");
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
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FindidActivity.this);    // 팝업창 생성
            if (data.equals("")) {                    // 값이 돌아오지 않을 경우 경고 팝업창
                dialog.dismiss();
                alertBuilder
                        .setTitle("알림")
                        .setMessage("회원 정보 없음")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeOptionsMenu();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
                text_id.setText("");
            } else {                               // 아이디를 받아올 경우
                dialog.dismiss();
                Toast.makeText(FindidActivity.this, "회원정보를 찾았습니다.", Toast.LENGTH_SHORT).show();
                text_id.setText(data);
            }
        }
    }
}
