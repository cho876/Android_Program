package com.example.cho.between;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog dialog;
    private ImageButton bLeft, bRight;
    private EditText ed_name, ed_email, ed_pw;
    private String sName, sEmail, sPw;
    private Check_Valid checkValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        initView();

    }

    private void initView() {
        dialog = new ProgressDialog(this);

        ed_name = (EditText) findViewById(R.id.join_name);
        ed_email = (EditText) findViewById(R.id.join_email);
        ed_pw = (EditText) findViewById(R.id.join_pw);

        bLeft = (ImageButton) findViewById(R.id.join_back);
        bLeft.setOnClickListener(this);
        bRight = (ImageButton) findViewById(R.id.join_next);
        bRight.setOnClickListener(this);

        checkValid = new Check_Valid(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.join_next:
                sName = ed_name.getText().toString();
                sEmail = ed_email.getText().toString();
                sPw = ed_pw.getText().toString();
                if (checkValid.isValidName(sName) && checkValid.isValidEmail(sEmail) && checkValid.isValidPw(sPw)) {
                    dialog.show();
                    JoinDB joinDB = new JoinDB();
                    joinDB.execute();
                }
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

            String param = "u_name=" + sName + "&u_id=" + sEmail + "&u_pw=" + sPw + "";
            Log.e("INFO", param);
            try {
                URL url = new URL(
                        "http://211.253.25.169/prac_join.php");
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
                if (data.equals("Success"))
                    Log.e("RESULT", "Success" + data);
                else
                    Log.e("RESULT", "Fail - " + data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (data.equals("Success")) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else if (data.equals("Fail")) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "회원정보가 중복됩니다.", Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
