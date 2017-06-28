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

public class LoginActivity extends AppCompatActivity implements OnClickListener {  // "로그인" 화면

    final int[] MY_BUTTONS = {
            R.id.join_layout,
            R.id.findid_layout,
            R.id.findpw_layout
    };

    private QuitHandler quitHandler;
    private EditText ed_id, ed_pw = null;
    private CheckBox cb_id = null;
    private String sId, sPw;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_id = (EditText) findViewById(R.id._login_id);
        ed_pw = (EditText) findViewById(R.id._login_pw);
        cb_id = (CheckBox) findViewById(R.id._login_chk);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();
        onChecked();
        quitHandler = new QuitHandler(this);

        for (int btnId : MY_BUTTONS) {
            ImageButton btn = (ImageButton) findViewById(btnId);
            btn.setOnClickListener(this);
        }
        Button btn = (Button)findViewById(R.id.login_layout);
        btn.setOnClickListener(this);
    }

    public void onChecked(){
        if(cb_id.isChecked()){
            sId = pref.getString("Id","");
            ed_id.setText(sId);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_layout:    // "로그인" 버튼 클릭 시,
                CheckEdit();
                break;
            case R.id.join_layout:    // "회원가입" 버튼 클릭 시,
                Intent go_join = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(go_join);
                break;
            case R.id.findid_layout:    // "회원정보 찾기" 버튼 클릭 시,
                Intent go_findid = new Intent(LoginActivity.this, FindidActivity.class);
                startActivity(go_findid);
                break;
            case R.id.findpw_layout:
                Intent go_findpw = new Intent(LoginActivity.this, FindpwActivity.class);
                startActivity(go_findpw);
        }
    }

    public void CheckEdit() {            // 로그인 시, ID or Password 입력 안할 시 메세지 발생
        try {
            sId = ed_id.getText().toString();
            sPw = ed_pw.getText().toString();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        loginDB IDB = new loginDB();
        IDB.execute();
    }

    public class loginDB extends AsyncTask<Void, Integer, Void> {
        String data;

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_id=" + sId + "&u_pw=" + sPw + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/login.php");
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);
            if (data.equals("1")) {
                editor.putString("Id", sId);
                editor.commit();
                Toast.makeText(LoginActivity.this, "회원인증 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent go_main = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(go_main);
                LoginActivity.this.finish();
            } else if (data.equals("0")) {
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
            } else {
                alertBuilder
                        .setTitle("알림")
                        .setMessage("등록정보가 없습니다.")
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        quitHandler.onBackPressed();
    }    // "Back"버튼 두 번 누를 시, 프로그램 종료

}