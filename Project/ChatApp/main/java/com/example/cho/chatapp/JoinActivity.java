package com.example.cho.chatapp;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    private EditText ed_email, ed_name, ed_pw, ed_pwchk;
    private Button btn;
    private String sEmail, sName, sPw, sPwchk;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        reference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        ed_email = (EditText) findViewById(R.id.join_email);
        ed_name = (EditText)findViewById(R.id.join_name);
        ed_pw = (EditText) findViewById(R.id.join_pw);
        ed_pwchk = (EditText)findViewById(R.id.join_pwchk);
        btn = (Button) findViewById(R.id.join_btn);

        dialog = new ProgressDialog(this);

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail = ed_email.getText().toString().trim();
                sName = ed_name.getText().toString().trim();
                sPw = ed_pw.getText().toString().trim();
                sPwchk = ed_pwchk.getText().toString().trim();
                if(!sPw.equals(sPwchk)) {
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUp(sEmail, sName, sPw);
            }
        });
    }

    public void signUp(final String sEmail, final String sName, final String sPw) {

        if (!isValidEmail(sEmail) || !isValidPW(sPw) || !isValidName(sName))
            return;

        dialog.setMessage("등록중입니다. 잠시 기다려주세요...");
        dialog.show();

        firebaseAuth.createUserWithEmailAndPassword(sEmail, sPw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserData userData = new UserData(sEmail, sName, sPw);
                            reference.child("users").push().setValue(userData);
                        } else {
                            //에러발생시
                            Toast.makeText(JoinActivity.this, "이메일이 중복됩니다.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

    }

             /*      이메일 유효성 검사     */
    private boolean isValidEmail(String sEmail) {
        if (sEmail == null || TextUtils.isEmpty(sEmail)) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
            return true;
    }

            /*      비밀번호 유효성 검사     */
    private boolean isValidPW(String sPw) {
        Pattern p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)");

        Matcher m = p.matcher(sPw);
            /*      최소 6자 이상, 한글 미포함       */
        if (m.find() && !sPw.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            return true;
        }else{
            Toast.makeText(this, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
            /*      이름 유효성 검사     */

    private boolean isValidName(String sName){
        if(sName == null || TextUtils.isEmpty(sName)){
            Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
