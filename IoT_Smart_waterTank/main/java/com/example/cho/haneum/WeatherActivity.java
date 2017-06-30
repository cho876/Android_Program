package com.example.cho.haneum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.tsengvn.typekit.TypekitContextWrapper;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {  // "현재 날씨" 화면

    final String urlname = "http://m.kma.go.kr/m/observation/observation_01.jsp";   // 기상청 URL
    final int[] MY_BUTTONS = {
            R.id.return_layout
    };
    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        webView = (WebView) findViewById(R.id.web_layout);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(urlname);

        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_layout:   // "돌아가기" 버튼 클릭 시,
                Intent go_main = new Intent(WeatherActivity.this, MainActivity.class);
                startActivity(go_main);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}

