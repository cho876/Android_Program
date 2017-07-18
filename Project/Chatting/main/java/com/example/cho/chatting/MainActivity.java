package com.example.cho.chatting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    private ChatAdapter chatAdapter;

    private ListView listView;
    private EditText ed_send;
    private String userName, userNick, userEmail;
    private int userRoom;
    private Button bSend;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("userDB", MODE_PRIVATE);
        userName = pref.getString("userName", "");
        userNick = pref.getString("userNick", "");
        userEmail = pref.getString("userEmail", "");
        userRoom = pref.getInt("userRoom", 0);
        initView();
        initFirebase();
    }


    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    /*  initialize View  */
    public void initView() {
        listView = (ListView) findViewById(R.id.main_listview);
        chatAdapter = new ChatAdapter(this, R.layout.listitem_another);
        chatAdapter.setUserEmail(userEmail);
        listView.setAdapter(chatAdapter);

        ed_send = (EditText) findViewById(R.id.main_send_txt);
        bSend = (Button) findViewById(R.id.main_sendBtn);
        bSend.setOnClickListener(this);

    }

    /*  initialize Firebase  */
    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("messages").child(String.valueOf(userRoom));
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {     // FirebaseDatabase 값 추가시,
                ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
                chatAdapter.add(chatDTO);
                listView.smoothScrollToPosition(chatAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_sendBtn:
                String message = ed_send.getText().toString();    // 메세지 내용 casting to String
                if (!TextUtils.isEmpty(message)) {
                    ed_send.setText("");
                    ChatDTO chatDTO = new ChatDTO(userName, userNick, userEmail, message, System.currentTimeMillis());   // ChatDTO Setting
                    databaseReference.push().setValue(chatDTO);
                }
                break;
        }
    }
}