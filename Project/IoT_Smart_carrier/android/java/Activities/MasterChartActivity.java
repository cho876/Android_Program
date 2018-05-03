package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.skcho.smartcarrier.Activities.Flagments.AgeFragment;
import com.example.skcho.smartcarrier.Activities.Flagments.SexFragment;
import com.example.skcho.smartcarrier.Customs.CustomTitle;
import com.example.skcho.smartcarrier.R;
import com.tsengvn.typekit.TypekitContextWrapper;


public class MasterChartActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btn_left, btn_right;
    private AgeFragment ageFragment;
    private SexFragment sexFragment;
    private CustomTitle customTitle;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_chart);
        init();
    }

    private void init() {
        ageFragment = new AgeFragment();
        sexFragment = new SexFragment();
        customTitle = (CustomTitle) findViewById(R.id.master_title);
        tv_title = customTitle.getTv_content();

        btn_left = (ImageButton) findViewById(R.id.master_left);
        btn_right = (ImageButton) findViewById(R.id.master_right);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        tv_title.setText("연령별 차트");
        getSupportFragmentManager().beginTransaction().replace(R.id.master_frag, ageFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.master_left:
                tv_title.setText("연령별 차트");
                getSupportFragmentManager().beginTransaction().replace(R.id.master_frag, ageFragment).commit();
                break;
            case R.id.master_right:
                tv_title.setText("성별별 차트");
                getSupportFragmentManager().beginTransaction().replace(R.id.master_frag, sexFragment).commit();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
