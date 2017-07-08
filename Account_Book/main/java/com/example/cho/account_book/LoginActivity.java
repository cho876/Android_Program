package com.example.cho.account_book;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.tsengvn.typekit.TypekitContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button blogin;
    private CustomImgBtn bLeft, bRight;    // 회원가입, 회원정보 찾기 Btn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        bLeft = (CustomImgBtn) findViewById(R.id.loginTojoin_btn);
        bLeft.setOnClickListener(this);
        bRight = (CustomImgBtn) findViewById(R.id.loginTofind_btn);
        bRight.setOnClickListener(this);

        blogin = (Button) findViewById(R.id.login_btn);
        blogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginTojoin_btn:                               // 회원가입 버튼 누를 시,
                Intent join = new Intent(this, JoinActivity.class);
                startActivity(join);
                break;

            case R.id.loginTofind_btn:                               // 회원정보 찾기 버튼 누를 시,
                Intent find = new Intent(this, FindIDActivity.class);
                startActivity(find);
                break;

            case R.id.login_btn:                                        // 로그인 버튼 누를 시,
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
