package com.example.cho.chatting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FindPwActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private CustomButton cb_search, cb_find, cb_home;
    private CustomEditText ced_name, ced_nick, ced_email;
    private EditText ed_name, ed_nick, ed_email;
    private String sName, sNick, sEmail;

    private ValidCheck validCheck;
    private UserDTO userDTO;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        initView();
        initFirebase();
    }

    public void initView() {
        cb_search = (CustomButton) findViewById(R.id.findPw_searchBtn);
        cb_search.setOnClickListener(this);
        cb_find = (CustomButton) findViewById(R.id.findPw_findidBtn);
        cb_find.setOnClickListener(this);
        cb_home = (CustomButton) findViewById(R.id.findPw_homeBtn);
        cb_home.setOnClickListener(this);

        ced_name = (CustomEditText) findViewById(R.id.findPw_name);
        ced_nick = (CustomEditText) findViewById(R.id.findPw_nick);
        ced_email = (CustomEditText) findViewById(R.id.findPw_email);
        ed_name = ced_name.getEd_content();
        ed_nick = ced_nick.getEd_content();
        ed_email = ced_email.getEd_content();

        validCheck = new ValidCheck(this);
        userDTO = new UserDTO();
        dialog = new ProgressDialog(this);
    }

    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findPw_searchBtn:
                dialog.setMessage("잠시만 기다려 주세요...");
                dialog.show();
                editToStr();
                MatchDB(sName, sNick, sEmail);
                break;
            case R.id.findPw_findidBtn:
                this.finish();
                break;
            case R.id.findPw_homeBtn:
                Intent intentHome = new Intent(FindPwActivity.this, LoginActivity.class);
                UtilFunc.UtilClose(intentHome);
                startActivity(intentHome);
                break;
        }
    }

    public void editToStr() {
        sName = ed_name.getText().toString();
        sNick = ed_nick.getText().toString();
        sEmail = ed_email.getText().toString();
    }

    public void MatchDB(String sName, String sNick, String sEmail) {
        if (!validCheck.isValidName(sName) || !validCheck.isValidName(sNick) || !validCheck.isValidEmail(sEmail)) {
            dialog.dismiss();
            return;
        }
        SearchDB(sName, sNick, sEmail);  // FirebaseDatabase에서 값 Search
    }

    public void SearchDB(final String sName, final String sNick, final String sEmail) {
        Query query = databaseReference.orderByChild("userEmail");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userDTO = snapshot.getValue(UserDTO.class);
                    Log.e("INFO", userDTO.getUserName() + " " + userDTO.getUserNick() + " " + userDTO.getUserEmail());
                    if (sName.equals(userDTO.getUserName()) && sNick.equals(userDTO.getUserNick()) && sEmail.equals(userDTO.getUserEmail())) {
                        Toast.makeText(FindPwActivity.this, "회원 정보를 찾았습니다.", Toast.LENGTH_SHORT).show();
                        viewAlertDialog(userDTO);   // AlertDialog를 통한 공지
                        return;
                    }
                }
                Toast.makeText(FindPwActivity.this, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*  AlertDialog View Setting  */
    public void viewAlertDialog(UserDTO userDTO) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FindPwActivity.this);    // 팝업창 생성
        alertBuilder
                .setTitle("검색 결과")
                .setMessage(userDTO.getUserPw())
                .setCancelable(true)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeOptionsMenu();
                    }
                });
        AlertDialog dialog = alertBuilder.create();
        dialog.show();
    }
}
