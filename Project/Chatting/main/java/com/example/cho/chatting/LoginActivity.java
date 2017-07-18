package com.example.cho.chatting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private CustomButton cb_Login, cb_Join, cb_Find;

    private CustomEditText ced_email, ced_pw;
    private EditText ed_email, ed_pw;
    private CheckBox checkBox;

    private String sEmail, sPw;

    private UserDTO userDTO;
    private ValidCheck validCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("userDB", MODE_PRIVATE);
        editor = pref.edit();
        Boolean idCheck = pref.getBoolean("userCheck", false);
        isFixed(idCheck);
        initView();
        initFirebase();
    }

    public void isFixed(Boolean check) {
        if (check) {
            ed_email.setText(pref.getString("userCheck", ""));
            checkBox.setChecked(true);
        }
    }

    /*  initialize View  */
    public void initView() {
        ced_email = (CustomEditText) findViewById(R.id.login_email);
        ed_email = ced_email.getEd_content();
        ced_pw = (CustomEditText) findViewById(R.id.login_pw);
        ed_pw = ced_pw.getEd_content();
        checkBox = (CheckBox) findViewById(R.id.login_fixId);

        cb_Login = (CustomButton) findViewById(R.id.login_loginBtn);
        cb_Login.setOnClickListener(this);
        cb_Join = (CustomButton) findViewById(R.id.login_joinBtn);
        cb_Join.setOnClickListener(this);
        cb_Find = (CustomButton) findViewById(R.id.login_findBtn);
        cb_Find.setOnClickListener(this);

        userDTO = new UserDTO();
        validCheck = new ValidCheck(this);
    }

    /*  initialize Firebase's Object  */
    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_loginBtn:           // When press Login Button
                editToStr();
                saveToSP(sEmail);    // Save to SharedPreference
                normalLogin(sEmail, sPw);
                break;

            case R.id.login_joinBtn:            // When press Join Button
                Intent intentJoin = new Intent(LoginActivity.this, JoinActivity.class);   // Move to JoinActivity
                startActivity(intentJoin);
                break;

            case R.id.login_findBtn:             // When press FindID Button
                Intent intentFind = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intentFind);
        }

    }

    /*  EditText To String  */
    public void editToStr() {
        sEmail = ed_email.getText().toString();
        sPw = ed_pw.getText().toString();
    }

    /*  normal Login Process  */
    public void normalLogin(final String sEmail, String sPw) {
        if (!validCheck.isValidEmail(sEmail) || !validCheck.isValidPw(sPw))      // If, Not valid email or password
            return;

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("등록 중입니다...");
        dialog.show();
        firebaseAuth.signInWithEmailAndPassword(sEmail, sPw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "로그인 실패했습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "로그인 성공했습니다. ", Toast.LENGTH_SHORT).show();
                            Intent intentMain = new Intent(LoginActivity.this, MenuActivity.class);     // Move to MainActivity
                            startActivity(intentMain);
                            finish();
                        }
                    }
                });
    }

    /* Save in SharedPreference  */
    public void saveToSP(String sEmail) {
        databaseReference = firebaseDatabase.getReference("users");
        Query query = databaseReference.orderByChild("userEmail").equalTo(sEmail);    // users 하위 query 중 (userEmail == sEmail)인 것 찾기
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {                // 찾을 경우
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userDTO = snapshot.getValue(UserDTO.class);
                    Log.e("LoginActivity", userDTO.getUserEmail());
                    editor.putString("userName", userDTO.getUserName());            // SharedPreference 내 저장
                    editor.putString("userEmail", userDTO.getUserEmail());
                    editor.putString("userImage", userDTO.getUserImg());
                    editor.putString("userNick", userDTO.getUserNick());
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onStop() {
        super.onStop();
        editor.putBoolean("userCheck", checkBox.isChecked());
        editor.putString("userEmail", sEmail);
        editor.commit();
    }
}