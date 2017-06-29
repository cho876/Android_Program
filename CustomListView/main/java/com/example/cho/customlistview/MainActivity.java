package com.example.cho.customlistview;
/*
        Title: 커스텀 리스트 뷰를 활용한 카카오톡 프로필 목록 만들기

 */
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;            // 프로필 목록이 들어갈 ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.liskView);

        dataSetting();
    }

    public void dataSetting(){
        mAdapter adapter = new mAdapter();              // ListView와 내부 View들을 이어주기 위한 Adapter 생성

        for(int i=0; i<10; i++){
            adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon), "name", "contents");    // 총 10개의 프로필 목록 생성
        }

        listView.setAdapter(adapter);
    }
}
