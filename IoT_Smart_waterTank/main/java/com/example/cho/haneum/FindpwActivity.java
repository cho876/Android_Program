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
    private TextView t_pw;
    private CustomEdit customEdit;
    private CustomButton customButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        customEdit = (CustomEdit) findViewById(R.id.custom_findPw_Email);      // 이메일 작성 란
        ed_email = customEdit.getEditText();

        customEdit = (CustomEdit) findViewById(R.id.custom_findPw_Id);         // 이름 작성 란
        ed_id = customEdit.getEditText();

        t_pw = (TextView) findViewById(R.id._findpw_search);                // 검색 결과 란
        bSearch = (Button) findViewById(R.id.searchpw_btn);                // 비밀번호 찾기 버튼
        bSearch.setOnClickListener(this);

        customButton = (CustomButton) findViewById(R.id.custom_findPw_btn);    // 돌아가기, 아이디 찾기 버튼
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);
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
                sId = ed_id.getText().toString();
                sEmail = ed_email.getText().toString();

                if (UtilCheck.isChecked(sId) && UtilCheck.isChecked(sEmail)) {
                    FindPW_DB findPW = new FindPW_DB();
                    findPW.execute();
                } else
                    Toast.makeText(this, "기입란을 채워주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class FindPW_DB extends AsyncTask<Void, Integer, Void> {
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
                t_pw.setText("");
            } else {
                Toast.makeText(FindpwActivity.this, "회원정보를 찾았습니다.", Toast.LENGTH_SHORT).show();
                t_pw.setText(data);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
