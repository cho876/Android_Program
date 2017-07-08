package com.example.cho.account_book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tsengvn.typekit.TypekitContextWrapper;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    private CustomImgBtn bLeft, bRight;            // 돌아가기, 저장 버튼\
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bLeft = (CustomImgBtn) findViewById(R.id.setTojoin_btn);
        bLeft.setOnClickListener(this);
        bRight = (CustomImgBtn) findViewById(R.id.setTomain_btn);
        bRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.setTojoin_btn:                               // 돌아가기 버튼 누를 시,
                this.finish();
                break;

            case R.id.setTomain_btn:                               // 저장하기 버튼 누를 시,
                Intent find = new Intent(this, JoinActivity.class);
                startActivity(find);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
