package com.example.cho.haneum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * 수조 내부 희망/현재 수온, 탁도, 수위, 히터, 서보모터, 펌프 상태 조회 Activity
 * Thread를 통해 3초 Delay 최신화
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {  // 메인 화면

    final int[] MY_BUTTONS = {
            R.id.register_layout,
            R.id.logout_layout,
            R.id.quit_layout
    };
    private SharedPreferences pref = null;

    private TextView t_title, tv_temp, tv_turb, tv_level, tv_heat, tv_in, tv_out;
    private String title;
    private boolean isChecked = true;
    private ProgressDialog dialog;

    private CustomTitle customTitle;
    private CustomTable customTable;

    private RetrofitConnect retrofitConnect;
    private QuitHandler quitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitConnect = new RetrofitConnect();

        initView();
        dialog.show();

        quitHandler = new QuitHandler(this);

        for (int btnid : MY_BUTTONS) {
            Button btn = (Button) findViewById(btnid);
            btn.setOnClickListener(this);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isChecked) {
                    connectToDB();
                    try {
                        Thread.sleep(3000);
                        dialog.dismiss();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * UI 초기화 함수
     */
    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려주세요...");
        customTitle = (CustomTitle) findViewById(R.id.custom_main_title);        // Title Custom View
        t_title = customTitle.getTv_content();
        t_title.setText(title + "'s\n어항 관리");
        t_title.setTextSize(20);

        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        title = pref.getString("Id", "");

        customTable = (CustomTable) findViewById(R.id.custom_main_temp);         // 수온/히터 수치 Custom View
        tv_temp = customTable.getTv_lcontents();
        tv_heat = customTable.getTv_rcontents();

        customTable = (CustomTable) findViewById(R.id.custom_main_turb);        // 탁도 / 배수 수치 Custom View
        tv_turb = customTable.getTv_lcontents();
        tv_out = customTable.getTv_rcontents();

        customTable = (CustomTable) findViewById(R.id.custom_main_level);        // 수위 / 급수 수치 Custom View
        tv_level = customTable.getTv_lcontents();
        tv_in = customTable.getTv_rcontents();
    }

    /**
     * "R.id.register_layout" -> "재등록" Button  (희망 수치 설정 창으로 이동)
     * "R.id.logout_layout" -> "로그아웃" Button  (로그인 화면으로 이동)
     * "R.id.quit_layout" -> "종료" Button        (어플 종료)
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_layout:   // "재등록" 버튼 클릭 시,
                Intent go_setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(go_setting);
                break;
            case R.id.logout_layout:   // "로그 아웃" 버튼 클릭 시,
                Intent go_login = new Intent(MainActivity.this, LoginActivity.class);
                UtilCheck.UtilClose(go_login);
                startActivity(go_login);
                break;
            case R.id.quit_layout:   // "종료" 버튼 클릭 시,
                quitHandler.onBackPressed();
        }
    }

    /**
     * Casting을 통해 String으로 받아온 데이터 int형으로 변환
     *
     * @param value (Casting할 String 데이터)
     * @return int형 데이터
     */
    public int castToInt(String value) {
        float fValue = Float.parseFloat(value);               // 희망 온도 소수점 이하부분 제거
        int iValue = (int) fValue;
        return iValue;
    }

    /**
     * 1 or 0으로 이루어진 Data를 바탕으로
     * "1 -> 동작 중 / 0 -> 비동작 중" 으로 TextView 최신화
     *
     * @param tv    (변경할 TextView)
     * @param value (1 or 0) <- 현재 모듈 제어 상태
     */
    public void onOffSet(TextView tv, String value) {          // On/Off 상태 UI 변경 함수
        if (value.equals("0"))
            tv.setText("비동작 중");
        else
            tv.setText("동작 중");
    }

    /**
     * RetrofitConnect 클래스의 Retrofit 통신을 통해 DB로부터 받아온 Json 데이터 사용
     * 직접적으로 UI값 변환에 관여하는 함수
     */
    private void connectToDB() {
        retrofitConnect.getDataByAsync(new RetrofitConnect.callBack<Data>() {
            @Override
            public void execute(Data data) {
                tv_temp.setText(castToInt(data.getSetTemp()) + " / " + castToInt(data.getCurTemp()));       // 설정 온도 + 현재 온도 값으로 온도 TextView Set
                tv_turb.setText(castToInt(data.getSetTurb()) + " / " + castToInt(data.getCurTurb()));       // 설정 탁도 + 현재 탁도 값으로 탁도 TextView Set

                if (data.getCtlLevel().equals("1"))
                    tv_level.setText("수위 부족");
                else
                    tv_level.setText("수위 충분");

                onOffSet(tv_heat, data.getCtlHeat());      // 현재 히터 제어 상태 처리 (0: 비동작 / else: 동작)
                onOffSet(tv_in, data.getCtlIn());      // 현재 급수 제어 상태 처리 (0: 비동작 / else: 동작)
                onOffSet(tv_out, data.getCtlOut());      // 현재 배수 제어 상태 처리 (0: 비동작 / else: 동작)

            }
        });
    }

    /**
     * Font 설정*
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    /**
     * "Back"버튼 두 번 누를 시, 프로그램 종료
     */
    @Override
    public void onBackPressed() {
        quitHandler.onBackPressed();
    }

    /**
     * Activity 종료 시, Thread 정지
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        isChecked = false;
    }
}