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
    private StorageReference storageReference;
    private ListView listView;
    private ChatAdapter chatAdapter;
    private EditText ed_send;
    private Button bSend;
    private TextView tv_desc, tv_time;
    private ImageView iv_profile;
    private FirebaseStorage storage;
    private String userName, userEmail,userNick;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("userDB", MODE_PRIVATE);
        Log.e("INFO", pref.getString("userEmail",""));
        userName = pref.getString("userName", "");
        userEmail = pref.getString("userEmail", "");
        userNick = pref.getString("userNick","");

        initView();
        initFirebase();
        updateProfile();
    }


    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    /*  initialize View  */
    public void initView() {
        tv_desc = (TextView) findViewById(R.id.main_profileContent);
        tv_time = (TextView) findViewById(R.id.main_profileTime);
        iv_profile = (ImageView) findViewById(R.id.main_profileImg);

        listView = (ListView) findViewById(R.id.main_listview);
        chatAdapter = new ChatAdapter(this, R.layout.listitem_another);
        listView.setAdapter(chatAdapter);

        ed_send = (EditText) findViewById(R.id.main_send_txt);
        bSend = (Button) findViewById(R.id.main_sendBtn);
        bSend.setOnClickListener(this);

    }

    /*  initialize Firebase  */
    public void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("messages");
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

    /* 자신의 프로필 update  */
    public void updateProfile() {
        StringBuilder profile = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("a h:mm", Locale.getDefault());
        Date now = new Date();
        String userTime = formatter.format(now);
        profile.append(userName).append("\n").append(userEmail);
        tv_desc.setText(profile);        // Profile description Setting
        tv_time.setText(userTime);       // Profile time Setting
        upldateImage();                        // Profile Image Setting

        chatAdapter.setUserEmail(userEmail);
        chatAdapter.notifyDataSetChanged();
    }

    /*  자신의 프로필 사진 Update  */
    public void upldateImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://chatdb-59f3b.appspot.com").child("images/");
        String fileName = userEmail;
        StorageReference mReference = storageReference.child(fileName);
        try{
            final File imgFile = File.createTempFile(fileName, "png");
            mReference.getFile(imgFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
                    iv_profile.setImageBitmap(bitmap);
                }
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}