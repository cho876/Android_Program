package com.example.cho.json;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_assets, tv_result;
    private EditText ed_fruit;
    private Button bAssets, bResult;
    private StringBuffer sb_info = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     * layout 초기화 함수
     */
    private void initView() {
        ed_fruit = (EditText) findViewById(R.id.edit_fruit_name);

        bAssets = (Button) findViewById(R.id.btn_to_assets);
        bAssets.setOnClickListener(this);
        bResult = (Button) findViewById(R.id.btn_to_client);
        bResult.setOnClickListener(this);

        tv_assets = (TextView) findViewById(R.id.txt_in_assets);
        tv_result = (TextView) findViewById(R.id.txt_in_server);
    }

    /**
     * Assets 폴더 내, Json 형식으로 작성한 fruit.txt 데이터 가져오기
     *
     * @return
     */
    private StringBuffer getTxtFile() {
        StringBuffer text = new StringBuffer();
        AssetManager manager = getResources().getAssets();
        InputStream is = null;
        try {
            is = manager.open("fruitJson");
            int line;
            char data;
            while ((line = is.read()) > 0) {
                data = (char) line;
                text.append(data);
            }
            Log.e("Json", text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_to_assets:                               // 누를 시, Assets 폴더 내, fruit.txt 데이터 반환
                sb_info = getTxtFile();
                tv_assets.setText(sb_info);
                break;

            case R.id.btn_to_client:                              // 누를 시, 원하는 과일의 가격 반환
                try {
                    JSONObject jsonObject = new JSONObject(sb_info.toString());
                    String fruitValue = jsonObject.getString("fruit");
                    JSONObject fruitObject = new JSONObject(fruitValue);

                    tv_result.setText(fruitObject.getString(ed_fruit.getText().toString()));
                } catch (JSONException e) {
                    tv_result.setText("해당 과일 없음");
                    e.printStackTrace();
                }
                break;
        }
    }
}