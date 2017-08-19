package com.example.cho.between;

import android.app.Fragment;
import android.content.Context;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction tran;
    private Fragment frag_info, frag_chat, frag_calendar;

    private Button bInfo, bChat, bCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
    }

    public void initFragment() {
        fragmentManager = getFragmentManager();
        frag_info = new Fragment_Info();
        frag_chat = new Fragment_Chat();
        frag_calendar = new Fragment_Calendar();
    }

    public void initView() {
        bInfo = (Button) findViewById(R.id.main_info);
        bInfo.setOnClickListener(this);
        bChat = (Button) findViewById(R.id.main_chat);
        bChat.setOnClickListener(this);
        bCalendar = (Button) findViewById(R.id.main_calendar);
        bCalendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_info:
                tran = fragmentManager.beginTransaction();
                tran.replace(R.id.main_container, frag_info);
                tran.commit();
                break;
            case R.id.main_chat:
                tran = fragmentManager.beginTransaction();
                tran.replace(R.id.main_container, frag_chat);
                tran.commit();
                break;
            case R.id.main_calendar:
                tran = fragmentManager.beginTransaction();
                tran.replace(R.id.main_container, frag_calendar);
                tran.commit();
                break;
        }
    }

    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(context));
    }
}
