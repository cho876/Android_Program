package com.example.cho.haneum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class WeatherActivity extends AppCompatActivity {  // "현재 날씨" 화면

    private ListView listView;
    private SharedPreferences pref;
    private CustomAdapter adapter;
    private String sTemp, sTurb, sLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.customlist_setting_list);
        adapter = new CustomAdapter();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Find_DB find_db = new Find_DB();
                    find_db.execute();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    public class Find_DB extends AsyncTask<Void, Integer, Void> {         // AsyncTask를 통한 서버 통신
        String data;
        String sId = pref.getString("Id", "");

        @Override
        protected Void doInBackground(Void... unused) {

            String param = "u_id=" + sId + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/display.php");
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

            String[] array = data.split(",");
            sTemp = array[0];
            sTurb = array[1];
            sLevel = array[2];
            if (listView.getCount() > 5)
                adapter.deleteItem(0);
            adapter.addItem(sTemp, sTurb, sLevel);
            listView.setAdapter(adapter);

        }
    }
}

