package com.example.cho.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_id, ed_pw, ed_name;
    private TextView tv_converse, tv_result;
    private Button bConverse, bResult;
    private StringBuffer sb_info = new StringBuffer();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();

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
        ed_id = (EditText) findViewById(R.id.id);
        ed_pw = (EditText) findViewById(R.id.pw);
        ed_name = (EditText) findViewById(R.id.name);

        bConverse = (Button) findViewById(R.id.btn_to_server);
        bConverse.setOnClickListener(this);
        bResult = (Button) findViewById(R.id.btn_to_client);
        bResult.setOnClickListener(this);

        tv_converse = (TextView) findViewById(R.id.resultConverse);
        tv_result = (TextView) findViewById(R.id.resultParsing);
    }

    @Override
    public void onClick(View view) {
        final String STARTJSON = "[";
        final String ENDJSON = "]";

        switch (view.getId()) {
            case R.id.btn_to_server:                               // 누를 시, Json 형식으로 변환
                if (!sb_info.toString().equals(""))
                    sb_info.append(",");

                String infoTemp = "{\"id\":" + "\"" + ed_id.getText().toString() + "\"" + ","           // Json 형식으로 입력 값 저장
                        + "\"name\":" + "\"" + ed_name.getText().toString() + "\"" + ","
                        + "\"pw\":" + "\"" + ed_pw.getText().toString() + "\"" + "}";
                sb_info.append(infoTemp);
                tv_converse.setText(STARTJSON + sb_info + ENDJSON);
                break;

            case R.id.btn_to_client:                              // 누를 시, Json 형식의 데이터에서 추출
                try {
                    JSONArray jsonArray = new JSONArray(tv_converse.getText().toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nameList.add(jsonObject.getString("name"));            // nameList에 name 값 저장
                        idList.add(jsonObject.getString("id"));                // idList에 id 값 저장
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tv_result.setText(nameList + "\n" + idList);                 // 저장된 ArrayList 값 반환
                break;
        }
    }
}