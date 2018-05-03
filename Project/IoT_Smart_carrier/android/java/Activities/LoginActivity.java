package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Utils.QuitHandler;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.UserEvent;
import com.example.skcho.smartcarrier.Services.Service.UserService;
import com.example.skcho.smartcarrier.Utils.EffectivenessMng;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {  // "로그인" 화면

    private UserService userService;

    final int[] MY_BUTTONS = {
            R.id.join_layout,
            R.id.findid_layout,
            R.id.findpw_layout
    };

    private EditText ed_id, ed_pw = null;
    private CheckBox cb_id = null;
    private boolean saveId = true;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;
    private EffectivenessMng effectivenessMng;
    private QuitHandler quitHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = new UserService();
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();
        initView();

        quitHandler = new QuitHandler(this);

        for (int btnId : MY_BUTTONS) {
            ImageButton btn = (ImageButton) findViewById(btnId);
            btn.setOnClickListener(this);
        }
        Button btn = (Button) findViewById(R.id.login_layout);
        btn.setOnClickListener(this);
    }

    public void initView() {
        ed_id = (EditText) findViewById(R.id._login_id);        // "아이디" 기입란
        ed_pw = (EditText) findViewById(R.id._login_pw);        // "비밀번호" 기입란
        cb_id = (CheckBox) findViewById(R.id._login_chk);       // "아이디 저장" 체크 박스

        saveId = pref.getBoolean("check", false);              // 체크 박스 현재 상태 true/false 저장 (Boolean)
        FixedID(saveId);                                       //  아이디 자동 입력 기능

        effectivenessMng = new EffectivenessMng(this);
    }

    public void FixedID(boolean saveId) {            // 아이디 자동 입력 기능
        if (saveId) {   // Check 상태 true일 경우,
            String id = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력
            ed_id.setText(id);
            cb_id.setChecked(saveId);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_layout:    // "로그인" 버튼 클릭 시,
                usePost(ed_id.getText().toString(), ed_pw.getText().toString());
                break;
            case R.id.join_layout:    // "회원가입" 버튼 클릭 시,
                Intent go_join = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(go_join);
                break;
            case R.id.findid_layout:    // "회원정보 찾기" 버튼 클릭 시,
                Intent go_findid = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(go_findid);
                break;
            case R.id.findpw_layout:
                Intent go_findpw = new Intent(LoginActivity.this, FindPwActivity.class);
                startActivity(go_findpw);
        }
    }

    public void usePost(String userId, String password){
        userService.getUserById("login", userId, password);
    }

    @Subscribe
    public void onServerEvent(UserEvent userEvent){
        if(!userEvent.getUserResponse().getUsername().equals("EMPTY")){
            editor.putString("Id", userEvent.getUserResponse().getUsername());     // Input to SharedPreference (Key: Id, value: sId)
            editor.commit();
            Toast.makeText(this, "Username: "+ userEvent.getUserResponse().getUsername()+
                    ", code: "+userEvent.getUserResponse().getResponseCode(), Toast.LENGTH_SHORT).show();
            Intent go_main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(go_main);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {        // "Back"버튼 두 번 누를 시, 프로그램 종료
        quitHandler.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
    @Override
    public void onStop() {      // Activity 생명 주기 중, onStop() 호출 시,
        super.onStop();
        editor.putBoolean("check", cb_id.isChecked());    //Activity 종료 전, CheckBox 상태 값 저장
        editor.commit();
    }
}