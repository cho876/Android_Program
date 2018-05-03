package com.example.skcho.smartcarrier.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.skcho.smartcarrier.Customs.CustomButton;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.Service.UserService;
import com.example.skcho.smartcarrier.Utils.TransitionsMng;
import com.example.skcho.smartcarrier.Utils.EffectivenessMng;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * 회원가입 Activity
 */

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {  // "회원가입" 화면

    private UserService userService;

    private Button bLeft, bRight;
    private String sName, sHome, sId, sEmail, sPw, sPw_chk, sSex, sAge;
    private Spinner sex_spinner, age_spinner;
    private EditText ed_name, ed_home, ed_id, ed_email, ed_pw, ed_pwchk;
    private EffectivenessMng effectivenessMng;
    private CustomButton customButton;
    private ProgressDialog dialog;
    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);        // SharedPreference  Setting
        editor = pref.edit();
        initView();
    }

    /**
     * UI 초기화 함수
     */
    public void initView() {
        userService = new UserService();
        sex_spinner = (Spinner) findViewById(R.id.join_sex);  // 성별 선택
        sex_spinner.setOnItemSelectedListener(this);
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner.setAdapter(arrayAdapter);

        age_spinner = (Spinner) findViewById(R.id.join_age);
        age_spinner.setOnItemSelectedListener(this);
        arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.age, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(arrayAdapter);

        ed_name = (EditText) findViewById(R.id.join_name);            // "이름" 기입 란
        ed_home = (EditText) findViewById(R.id.join_home);
        ed_id = (EditText) findViewById(R.id.join_id);                // "아이디" 기입 란
        ed_email = (EditText) findViewById(R.id.join_email);
        ed_pw = (EditText) findViewById(R.id.join_pw);                // "비밀번호" 기입 란
        ed_pwchk = (EditText) findViewById(R.id.join_pwchk);          // "비밀번호 확인" 기입 란

        customButton = (CustomButton) findViewById(R.id.custom_join_btn);      // 돌아가기, 화인 버튼
        bLeft = customButton.getLeftBtn();
        bLeft.setOnClickListener(this);
        bRight = customButton.getRightBtn();
        bRight.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        effectivenessMng = new EffectivenessMng(this);
    }

    public void usePost(String userId, String password, String name, String address, String email, String sex, String age){
        userService.setNewUser(userId, password, name, address, email, sex, age);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_right:
                if(isCorrected()){
                    dialog.dismiss();
                    usePost(sId, sPw, sName, sHome, sEmail, sSex, sAge);

                    Intent go_login = new Intent(JoinActivity.this, LoginActivity.class);   // 회원가입 -> 로그인 창 이동
                    TransitionsMng.UtilClose(go_login);
                    startActivity(go_login);
                }
                break;
            case R.id.button_left:
                Intent go_login = new Intent(JoinActivity.this, LoginActivity.class);   // 회원가입 -> 로그인 창 이동
                TransitionsMng.UtilClose(go_login);
                startActivity(go_login);
                break;
        }
    }

    public boolean isCorrected(){
        boolean isCorrect = false;
        sName = ed_name.getText().toString();        // 이름 변환
        sHome = ed_home.getText().toString();
        sId = ed_id.getText().toString();            // 아이디 변환
        sEmail = ed_email.getText().toString();
        sPw = ed_pw.getText().toString();            // 비밀번호 변환
        sPw_chk = ed_pwchk.getText().toString();      // 비밀번호 확인 변환


        dialog.setMessage("등록 중입니다...");
        dialog.show();
        if (effectivenessMng.isWrited(sName, sHome, sId, sEmail, sPw, sPw_chk)) {                                            // 빈칸 공백 X,
            if (effectivenessMng.isSamePw(sPw, sPw_chk)) {     // 비밀번호 && 비밀번호 확인
                if (!effectivenessMng.isValidName(sName))                   // 이름 형식과 어긋날 시,
                    dialog.dismiss();
                else if (!effectivenessMng.isValidId(sId))                   // 아이디 형식과 어긋날 시,
                    dialog.dismiss();
                else if (!effectivenessMng.isValidEmail(sEmail))
                    dialog.dismiss();
                else if (!effectivenessMng.isValidPw(sPw))                   // 비밀번호 형식과 어긋날 시,
                    dialog.dismiss();
                else {                                                 // 모두 충족 시,
                    editor.putString("Id", sId);     // Input to SharedPreference (Key: Id, value: sId)
                    editor.commit();
                    isCorrect = true;
                }
            }
            else                                      // 비밀번호 != 비밀번호 학인
                dialog.dismiss();
        } else                                                                                                // 빈칸 공백 O
            dialog.dismiss();
        return isCorrect;
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == sex_spinner) {
            sSex = parent.getItemAtPosition(position).toString();
        } else if (parent == age_spinner) {
            sAge = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}