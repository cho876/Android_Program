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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FindpwActivity extends AppCompatActivity implements View.OnClickListener {  // "비밀번호 찾기" 화면

    private Button bLeft, bRight, bSearch;
    private String sId, sEmail;
    private EditText ed_id, ed_email;
    private TextView tv_pw;
    private CustomButton customButton;
    private ValidCheck validCheck;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);
        initView();
    }

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

        dialog = new ProgressDialog(this);
        validCheck = new ValidCheck(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right:  // "아이디 찾기"버튼 클릭 시,
                Intent go_id = new Intent(FindpwActivity.this, FindidActivity.class);
                go_id.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(go_id);
                break;
            case R.id.button_left:  // "홈으로"버튼 클릭 시,
                Intent go_login = new Intent(this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
            case R.id.searchpw_btn:
                writeToDB();
                break;
        }
    }

    public void writeToDB() {         // DB 내, 저장 Func
        sId = ed_id.getText().toString();
        sEmail = ed_email.getText().toString();

        dialog.setMessage("비밀번호를 찾는 중입니다...");
        dialog.show();
        if (validCheck.isWrited(sId, sEmail)) {
            JoinDB joinDB = new JoinDB();
            joinDB.execute();
        }else
            dialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    //////////////////////////////////        JoinDB            ////////////////////////////////////

                /*            AsyncTask를 통한 Threading 작업 class       */

    public class JoinDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_id=" + sId + "&u_email=" + sEmail + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/findPW.php");
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
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FindpwActivity.this);
            dialog.dismiss();
            if (data.equals("")) {
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
                tv_pw.setText("");
            } else {
                dialog.dismiss();
                Toast.makeText(FindpwActivity.this, "회원정보를 찾았습니다.", Toast.LENGTH_SHORT).show();
                tv_pw.setText(data);
            }
        }
    }
}
