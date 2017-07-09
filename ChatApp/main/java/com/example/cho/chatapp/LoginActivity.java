package com.example.cho.chatapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    public EditText ed_email, ed_pw;
    public String sEmail, sPw;
    public Button btn;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;
    private DatabaseReference reference;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        ed_email = (EditText) findViewById(R.id.login_email);
        ed_pw = (EditText) findViewById(R.id.login_pw);
        btn = (Button) findViewById(R.id.login_btn);

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Toast.makeText(LoginActivity.this, "환영합니다. " + user.getEmail(), Toast.LENGTH_SHORT).show();
            }
        };

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail = ed_email.getText().toString().trim();
                sPw = ed_pw.getText().toString().trim();
                signIn(sEmail, sPw);
            }
        });
    }

    public void signIn(final String sEmail, final String sPw) {
        dialog.setMessage("로그인 중...");
        dialog.show();

        auth.signInWithEmailAndPassword(sEmail, sPw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("INFO", sEmail + " " + sPw);
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }
}
