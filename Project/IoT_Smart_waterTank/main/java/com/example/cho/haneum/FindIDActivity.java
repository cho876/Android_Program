package com.example.cho.haneum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

/**
 * 아이디 찾기 Activity
 * <p>
 * Retrofit 통신 활용
 * userName, userEmail을 통한 userID 반환 Activity
 */

public class FindIDActivity extends AppCompatActivity implements View.OnClickListener {  // "아이디 찾기" 화면

    private Button bLeft, bRight, bSearch;
    private EditText ed_name, ed_email;    // 이름, 이메일 기입란
    private TextView text_id;             // 아이디 찾기 결과 변수
    private CustomButton customButton;
    private ValidCheck validCheck;
    private RetrofitConnect retrofitConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);
        initView();
        retrofitConnect = new RetrofitConnect();
        validCheck = new ValidCheck(this);
    }

    /**
     * UI 초기화 함수
     */
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
    }

    /**
     * 버튼에 따른 기능 부여
     * "R.id.searchId_btn" -> "아이디 찾기" Button  (아이디 찾기 결과 출력)
     * "R.id.button_right" -> "비밀번호 찾기" Button  (비밀번호 찾기 창으로 이동)
     * "R.id.button_left" -> "돌아가기" Button    (이전 창으로 이동)
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchId_btn:
                editToStr();
                break;
            case R.id.button_right:
                Intent go_pw = new Intent(FindIDActivity.this, FindPWActivity.class);
                startActivity(go_pw);
                break;
            case R.id.button_left:
                Intent go_login = new Intent(this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
        }
    }

    /**
     * EditText로 기입한 데이터 String으로 변환
     * ConnectToDB로 변환시킨 String값 전달
     */
    public void editToStr() {           // DB 내, 저장 Func
        String sName = ed_name.getText().toString();     // EditText -> String으로 변환
        String sEmail = ed_email.getText().toString();

        if (validCheck.isWrited(sName, sEmail))        // 두 EditText 모두 기입할 시,
            connectToDB(sName, sEmail, text_id);
    }

    /**
     * Retrofit 통신을 통해 userName, userEmail에 해당하는 userID 반환 및 TextView 수정
     *
     * @param userName  (유저 이름)
     * @param userEmail (유저 이메일)
     * @param tv        (변경할 TextView)
     */
    private void connectToDB(String userName, String userEmail, final TextView tv) {
        retrofitConnect.getUserIdByAsync(userName, userEmail, new RetrofitConnect.callBack<Users>() {
            @Override
            public void execute(Users user) {
                tv.setText(user.getUserID());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
