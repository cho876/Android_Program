package com.example.cho.account_book;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int TAB1 = 1;
    private final int TAB2 = 2;
    private final int TAB3 = 3;


    private Button bTab1, bTab2, bTab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTab1 = (Button) findViewById(R.id.main_tab1);
        bTab1.setOnClickListener(this);
        bTab2 = (Button) findViewById(R.id.main_tab2);
        bTab2.setOnClickListener(this);
        bTab3 = (Button) findViewById(R.id.main_tab3);
        bTab3.setOnClickListener(this);

        callFragment(TAB1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tab1:
                callFragment(TAB1);
                break;
            case R.id.main_tab2:
                callFragment(TAB2);
                break;
            case R.id.main_tab3:
                callFragment(TAB3);
                break;
        }
    }

    private void callFragment(int fragment_no) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment_no) {
            case 1:
                Tab1 tab1 = new Tab1();
                transaction.replace(R.id.tab_container, tab1);
                transaction.commit();
                break;
            case 2:
                Tab2 tab2 = new Tab2();
                transaction.replace(R.id.tab_container, tab2);
                transaction.commit();
                break;
            case 3:
                Tab3 tab3 = new Tab3();
                transaction.replace(R.id.tab_container, tab3);
                transaction.commit();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}