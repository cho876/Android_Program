package com.example.cho.account_book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tsengvn.typekit.TypekitContextWrapper;

public class FindIDActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomImgBtn bLeft, bRight;     // 돌아가기, 비밀번호 찾기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        bLeft = (CustomImgBtn) findViewById(R.id.findidTologin_btn);
        bLeft.setOnClickListener(this);
        bRight = (CustomImgBtn) findViewById(R.id.findidTofindpw_btn);
        bRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findidTologin_btn:                               // 돌아가기 버튼 누를 시,
                this.finish();
                break;

            case R.id.findidTofindpw_btn:                               // 비밀번호 찾기 버튼 누를 시,
                Intent find = new Intent(this, FindPWActivity.class);
                startActivity(find);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
