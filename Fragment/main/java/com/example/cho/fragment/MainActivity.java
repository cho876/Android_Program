package com.example.cho.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
    Title: Fragment를 활용한 화면 설계
            Fragment A는 상단 고정
            Fragment B와 C는 버튼을 누를 시, 계속해서 바뀜
 */

public class MainActivity extends AppCompatActivity {

    private boolean isFragmentB = true;        // 초기 Fragment 화면 설정: Fragment B

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {        // Button 누를 시, switchFragment() 호출
                switchFragment();
            }
        });
    }

    public void switchFragment() {
        Fragment fr;

        if (isFragmentB)             // 초기 Fragment = fragment_b.xml
            fr = new FragmentB();
        else                          // 버튼 누를 시, Fragment = fragment_c.xml
            fr = new FragmentC();

        isFragmentB = (isFragmentB) ? false : true;       // fragmentB 화면 출력후, false로 바뀜

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentBC, fr);                // 현재 fr 값으로 frameLayout 화면 변경
        transaction.commit();
    }
}
