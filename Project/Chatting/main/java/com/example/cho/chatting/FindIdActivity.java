package com.example.cho.chatting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FindIdActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProgressDialog dialog;
    private CustomButton cb_search, cb_back, cb_find;
    private CustomEditText ced_name, ced_nick;
    private EditText ed_name, ed_nick;
    private String sName, sNick;

    private ValidCheck validCheck;
    private UserDTO userDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);
        initView();
        initFirebase();
    }

    /*  Initialize View  */
    public void initView() {
        ced_name = (CustomEditText) findViewById(R.id.findId_name);
        ed_name = ced_name.getEd_content();
        ced_nick = (CustomEditText) findViewById(R.id.findId_nick);
        ed_nick = ced_nick.getEd_content();

        cb_search = (CustomButton) findViewById(R.id.findId_searchBtn);
        cb_search.setOnClickListener(this);
        cb_back = (CustomButton) findViewById(R.id.findId_backBtn);
        cb_back.setOnClickListener(this);
        cb_find = (CustomButton) findViewById(R.id.findId_findpwBtn);
        cb_find.setOnClickListener(this);

        validCheck = new ValidCheck(this);
        userDTO = new UserDTO();
        dialog = new ProgressDialog(this);
    }

    /*  Initialize Firebase */
    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findId_searchBtn:
                dialog.setMessage("잠시만 기다려 주세요..");
                dialog.show();
                editToStr();
                MatchDB(sName, sNick);
                break;

            case R.id.findId_backBtn:
                this.finish();
                break;

            case R.id.findId_findpwBtn:
                Intent intentFind = new Intent(FindIdActivity.this, FindPwActivity.class);
                startActivity(intentFind);
                break;
        }
    }

    /*  EditText casting to String  */
    public void editToStr() {
        sName = ed_name.getText().toString();
        sNick = ed_nick.getText().toString();
    }

    /*  검색 시작  */
    public void MatchDB(String sName, String sEmail) {
        /*  기입란 공백 존재 시 */
        if (!validCheck.isValidName(sName) || !validCheck.isValidName(sNick)) {     // When Invalid values
            dialog.dismiss();
            return;
        }

        SearchDB(sName, sNick);  // FirebaseDatabase에서 값 Search
        return;
    }

    /*  FirebaseDatabase 내부 진입  */
    public void SearchDB(final String sName, final String sNick) {
        Query query = databaseReference.orderByChild("userName");      // users 하위 child key 값 중 userName에 Focus
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {     // Key값인 userName를 찾을 경우
                dialog.dismiss();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userDTO = snapshot.getValue(UserDTO.class);          // userDTO 내부에 값 저장

                    /*  Query에서 찾아온 값과 입력한 값이 같을 경우 */
                    if (sName.equals(userDTO.getUserName()) && sNick.equals(userDTO.getUserNick())) {
                        Toast.makeText(FindIdActivity.this, "회원 정보를 찾았습니다.", Toast.LENGTH_SHORT).show();
                        viewAlertDialog(userDTO);   // AlertDialog를 통한 공지
                        return;
                    }
                }
                Toast.makeText(FindIdActivity.this, "회원 정보가 없습니다..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*  AlertDialog View Setting  */
    public void viewAlertDialog(UserDTO userDTO) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(FindIdActivity.this);    // 팝업창 생성
        alertBuilder
                .setTitle("검색 결과")
                .setMessage(userDTO.getUserEmail())
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
