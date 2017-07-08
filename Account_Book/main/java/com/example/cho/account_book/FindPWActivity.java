package com.example.cho.account_book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tsengvn.typekit.TypekitContextWrapper;

public class FindPWActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomImgBtn bLeft, bRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);


        bLeft = (CustomImgBtn) findViewById(R.id.findpwTofindid_btn);
        bLeft.setOnClickListener(this);
        bRight = (CustomImgBtn) findViewById(R.id.findpwTologin_btn);
        bRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findpwTofindid_btn:                               // 아이디 찾기 버튼 누를 시,
                this.finish();
                break;

            case R.id.findpwTologin_btn:                               // 돌아가기 버튼 누를 시,
                Intent home = new Intent(this, LoginActivity.class);
                startActivity(home);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
