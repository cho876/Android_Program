package com.example.cho.account_book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.tsengvn.typekit.TypekitContextWrapper;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomImgBtn bLeft, bRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        bLeft = (CustomImgBtn) findViewById(R.id.joinTologin_btn);
        bLeft.setOnClickListener(this);
        bRight = (CustomImgBtn) findViewById(R.id.joinToset_btn);
        bRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.joinTologin_btn:                               // 돌아가기 버튼 누를 시,
                this.finish();
                break;

            case R.id.joinToset_btn:                               // 가계부 설정 버튼 누를 시,
                Intent find = new Intent(this, SettingActivity.class);
                startActivity(find);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
