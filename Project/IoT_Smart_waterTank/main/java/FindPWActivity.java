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
 * 비밀번호 찾기 Activity
 */

public class FindPWActivity extends AppCompatActivity implements View.OnClickListener {  // "비밀번호 찾기" 화면

    private Button bLeft, bRight, bSearch;
    private EditText ed_id, ed_email;
    private TextView tv_pw;
    private CustomButton customButton;
    private ValidCheck validCheck;
    private RetrofitConnect retrofitConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);
        initView();
        retrofitConnect = new RetrofitConnect();
        validCheck = new ValidCheck(this);
    }

    /**
     * UI 초기화 함수
     */
    public void initView() {
        ed_email = (EditText) findViewById(R.id.findPw_Email);      // 이메일 작성 란
        ed_id = (EditText) findViewById(R.id.findPw_Id);        // 이름 작성 란

        tv_pw = (TextView) findViewById(R.id._findpw_search);                // 검색 결과 란
        bSearch = (Button) findViewById(R.id.searchpw_btn);                // 비밀번호 찾기 버튼
        bSearch.setOnClickListener(this);

        customButton = (CustomButton) findViewById(R.id.custom_findPw_btn);    // 돌아가기, 아이디 찾기 버튼
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);
    }

    /**
     * 버튼에 따른 기능 부여
     * "R.id.searchId_btn" -> "비밀번호 찾기" Button (비밀번호 찾기 결과 출력)
     * "R.id.button_right" -> "아이디 찾기" Button   (아이디 찾기 창으로 이동)
     * "R.id.button_left" -> "돌아가기" Button       (이전 창으로 이동)
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchpw_btn:
                editToStr();
                break;
            case R.id.button_right:
                Intent go_id = new Intent(FindPWActivity.this, FindIDActivity.class);
                go_id.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(go_id);
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
    public void editToStr() {         // DB 내, 저장 Func
        String sId = ed_id.getText().toString();
        String sEmail = ed_email.getText().toString();

        if (validCheck.isWrited(sId, sEmail))
            connectToDB(sEmail, sId, tv_pw);
    }

    /**
     * Retrofit 통신을 통해 userEmail, userId에 해당하는 userPW 반환 및 TextView 수정
     *
     * @param userEmail (유저 이메일)
     * @param userId    (유저 아이디)
     * @param tv        (변경할 TextView)
     */
    private void connectToDB(String userEmail, String userId, final TextView tv) {
        retrofitConnect.getUserPwByAsync(userEmail, userId, new RetrofitConnect.callBack<Users>() {
            @Override
            public void execute(Users user) {
                tv.setText(user.getUserPW());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
