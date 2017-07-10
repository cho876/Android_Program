package com.example.cho.howtogooglelogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public final static int RC_SIGN_IN = 1001;
    private SignInButton signInButton;
    private Button button;
    private String sEmail, sPw;
    private EditText ed_email, ed_pw;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFirebase();
    }

         /*  View 초기화    */
    public void initView() {
        signInButton = (SignInButton) findViewById(R.id.googleLogin);
        signInButton.setOnClickListener(this);
        ed_email = (EditText)findViewById(R.id.email);
        ed_pw = (EditText)findViewById(R.id.password);
        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);
    }

        /*  Firebase 관련 초기화     */
    public void initFirebase() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.googleLogin:
                signIn();   // 구글 로그인 시도
                break;
            case R.id.login:
                sEmail = ed_email.toString().trim();
                sPw = ed_pw.toString().trim();
                createUser(sEmail, sPw);
        }
    }


 ////////////////////////////////////////////////// 회원가입 구현 //////////////////////////////////////////////////


    public void createUser(String sEmail, String sPw) {
        firebaseAuth.createUserWithEmailAndPassword(sEmail, sPw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(MainActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    }
                });
    }

////////////////////////////////////////////////// 구글 로그인 구현 //////////////////////////////////////////////////

        /*      회원가입 Func       */

    public void signIn() {
        /*      구글 사용자인지 확인하러 이동    */
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                /*      구글 사용자로 판별됐을 시, Firebase로 정보 이동 - > firebaseAuthWithGoogle()     */
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);  //
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        /*      Google에서 정보 받고 이를 Firebase로 전달      */
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
