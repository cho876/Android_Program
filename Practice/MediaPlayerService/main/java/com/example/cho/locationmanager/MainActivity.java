package com.example.cho.locationmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bPlay, bStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVIew();
    }

    public void initVIew() {
        bPlay = (Button) findViewById(R.id.play);
        bPlay.setOnClickListener(this);
        bStop = (Button) findViewById(R.id.stop);
        bStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MusicService.class);

        if(view.getId() == R.id.play){
            intent.putExtra(MusicService.MESSEAGE_KEY, true);   // 재생버튼 누를 시, 음악 시작
        }else{
            intent.putExtra(MusicService.MESSEAGE_KEY, false);  // 정지버튼 누를 시, 음악 정지
        }
        startService(intent);
    }
}
