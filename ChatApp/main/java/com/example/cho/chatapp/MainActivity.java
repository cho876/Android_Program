package com.example.cho.chatapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChildEventListener;
    private ArrayAdapter<ChatData> mAdapter;
    private ListView mListView;
    private EditText mEditText;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFirebaseDatabase();
        initValues();
    }

    public void initViews() {
        mListView = (ListView) findViewById(R.id.main_list);
        mAdapter = new ChatAdapter(this, R.layout.listitem);
        mListView.setAdapter(mAdapter);
        mEditText = (EditText) findViewById(R.id.main_chat);
        findViewById(R.id.main_btn).setOnClickListener(this);
    }

    public void initFirebaseDatabase() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                Log.e("INIT 1",chatData.Msg);
                mAdapter.add(chatData);
                Log.e("INIT FIREBASE",chatData.Msg);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebasekey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseKey.equals(firebasekey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mReference.addChildEventListener(mChildEventListener);
    }

    private void initValues() {
        userName = "Guest" + new Random().nextInt(5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChildEventListener);
    }

    @Override
    public void onClick(View v) {
        String message = mEditText.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            Log.e("ONCLICK",message);
            mEditText.setText("");
            ChatData chatData = new ChatData();
            chatData.userName = userName;
            chatData.Msg = message;
            chatData.time = System.currentTimeMillis();
            mReference.push().setValue(chatData);
        }
    }
}