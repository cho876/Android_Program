package com.example.cho.chatting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private Uri filePath;
    private ImageView iv_profile;
    private CustomEditText ced_name, ced_nick, ced_email, ced_pw, ced_pwChk;
    private EditText ed_name, ed_nick, ed_email, ed_pw, ed_pwChk;
    private String sName, sNick, sEmail, sPw, sPwchk;
    private CustomButton cb_search, cb_back, cb_regist;
    private ValidCheck validCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        initView();
        initFirebase();
    }

    public void initView() {
        iv_profile = (ImageView) findViewById(R.id.join_Img);

        ced_name = (CustomEditText) findViewById(R.id.join_name);
        ed_name = ced_name.getEd_content();
        ced_nick = (CustomEditText) findViewById(R.id.join_nickname);
        ed_nick = ced_nick.getEd_content();
        ced_email = (CustomEditText) findViewById(R.id.join_email);
        ed_email = ced_email.getEd_content();
        ced_pw = (CustomEditText) findViewById(R.id.join_password);
        ed_pw = ced_pw.getEd_content();
        ced_pwChk = (CustomEditText) findViewById(R.id.join_passwordChk);
        ed_pwChk = ced_pwChk.getEd_content();

        cb_search = (CustomButton) findViewById(R.id.join_searchBtn);
        cb_search.setOnClickListener(this);
        cb_back = (CustomButton) findViewById(R.id.join_backBtn);
        cb_back.setOnClickListener(this);
        cb_regist = (CustomButton) findViewById(R.id.join_saveBtn);
        cb_regist.setOnClickListener(this);

        validCheck = new ValidCheck(this);
    }

    public void initFirebase() {
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.join_searchBtn:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
                break;
            case R.id.join_saveBtn:
                editToStr();    // EditText's Value Casting to String
                uploadToStorage();
                break;
            case R.id.join_backBtn:
                this.finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                iv_profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*  EditText To String  */
    public void editToStr() {
        sName = ed_name.getText().toString();
        sNick = ed_nick.getText().toString();
        sEmail = ed_email.getText().toString();
        sPw = ed_pw.getText().toString();
        sPwchk = ed_pwChk.getText().toString();
    }

    /*  Save in FirebaseAuth  */
    public void signIn(String sName, String sEmail, String sPw, String sPwchk) {
        firebaseAuth.createUserWithEmailAndPassword(sEmail, sPw)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {     // FirebaseAuth 작업 완료 시,
                        uploadToDB(filePath);        // FirebaseDatabase 작업 시작
                    }
                });
    }

    /*  Save in FirebaseDatabase  */
    private void uploadToDB(Uri filePath) {
        UserDTO userDTO = new UserDTO();       // UserDTO Setting
        userDTO.setUserImg(filePath.toString());
        userDTO.setUserName(sName);
        userDTO.setUserNick(sNick);
        userDTO.setUserEmail(sEmail);
        userDTO.setUserPw(sPw);
        database.getReference().child("users").child(userDTO.getUserName()).setValue(userDTO);   // (root: Users / 사용자 이메일 / userDTO) Save
    }

    /* Save in FirebaseStorage  */
    private void uploadToStorage() {
        if (!validCheck.isSamePw(sPw, sPwchk) || !validCheck.isValidName(sName) || !validCheck.isValidName(sNick)     //  When write Invalid Values
                || !validCheck.isValidEmail(sEmail) || !validCheck.isValidPw(sPw)) {
            return;
        }

        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("업로드 중...");
            dialog.show();

            String fileName = sEmail;       // 그림 파일 이름: 현재 사용자 이메일 주소
            final StorageReference storageReference = storage.getReferenceFromUrl("gs://chatdb-59f3b.appspot.com").child("images/" + fileName);

            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {     // 사진 파일 업로드 성공 시,
                            dialog.dismiss();

                            signIn(sName, sEmail, sPw, sPwchk);           // FirebaseAuth 작업 시작
                            Toast.makeText(JoinActivity.this, "등록을 완료했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {                            // 사진 파일 업로드 실패 시,
                            dialog.dismiss();
                            Toast.makeText(JoinActivity.this, "등록을 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {      // 사진 파일 업로드 진행 중일 시,
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            dialog.setMessage("등록 중 (" + (int) progress + "%)...");
                        }
                    });
        } else {     // 파일 등록 안되어있을 시,
            Toast.makeText(JoinActivity.this, "파일을 먼저 등록해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
