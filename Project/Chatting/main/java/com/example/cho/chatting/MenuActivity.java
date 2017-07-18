package com.example.cho.chatting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.google.firebase.storage.StorageReference;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ImageView iv_img;
    private TextView tv_userContent, tv_userTime;
    private ListView lv;
    private MenuAdapter menuAdapter;

    private FloatingActionButton fab_main, fab_plus, fab_logout;
    private Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;

    private SimpleDateFormat formatter;
    private Date now;
    private String sDate;
    private String sTitle;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        pref = getSharedPreferences("userDB", MODE_PRIVATE);
        editor = pref.edit();
        initFirebase();
        initView();
        initFab();
        pressListview();
    }

    /*  Initialize View  */
    public void initView() {
        tv_userContent = (TextView) findViewById(R.id.menu_profileContent);
        tv_userTime = (TextView) findViewById(R.id.menu_profileTime);
        iv_img = (ImageView) findViewById(R.id.menu_profileimg);

        updateProfile();        // 프로필 업데이트
        lv = (ListView) findViewById(R.id.menu_list);
        menuAdapter = new MenuAdapter(this, R.layout.menu_list_item);
        lv.setAdapter(menuAdapter);
    }

    /*  Initialize FloatingButton   */
    public void initFab() {
        fab_main = (FloatingActionButton) findViewById(R.id.menu_fab);
        fab_main.setOnClickListener(this);
        fab_plus = (FloatingActionButton) findViewById(R.id.menu_fab_new);
        fab_logout = (FloatingActionButton) findViewById(R.id.menu_fab_logout);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabAnticlockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
    }

    /*  Initialize Firebase  */
    public void initFirebase() {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://chatdb-59f3b.appspot.com").child("images/");
    }

    /*  Update user's profile  */
    public void updateProfile() {
        formatter = new SimpleDateFormat("a h:mm", Locale.getDefault());
        now = new Date();
        String sDate = formatter.format(now);
        tv_userContent.setText(pref.getString("userName", "") + "\n" + pref.getString("userEmail", ""));
        tv_userTime.setText(sDate);
        upldateImage();    // Image Update
    }


    /*   Update user's image */
    public void upldateImage() {
        String fileName = pref.getString("userEmail", "");
        StorageReference mReference = storageReference.child(fileName);
        try {
            final File imgFile = File.createTempFile(fileName, "png");
            mReference.getFile(imgFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getPath());
                    iv_img.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_fab:
                if (isOpen) {
                    fab_main.startAnimation(fabAnticlockwise);
                    fab_plus.startAnimation(fabClose);
                    fab_logout.startAnimation(fabClose);
                    fab_plus.setClickable(false);
                    fab_logout.setClickable(false);
                    isOpen = false;
                } else {
                    fab_main.startAnimation(fabClockwise);
                    fab_plus.startAnimation(fabOpen);
                    fab_logout.startAnimation(fabOpen);
                    fab_plus.setOnClickListener(this);
                    fab_logout.setOnClickListener(this);
                    isOpen = true;
                }
                break;

            case R.id.menu_fab_new:
                openChat();
                break;

            case R.id.menu_fab_logout:
                logOut();
                break;
        }
    }

    /*  채팅방 추가  */
    public void openChat() {
        final EditText ed_title = new EditText(this);
        ed_title.setHint("채팅방 이름 입력");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("채팅방 만들기")
                .setCancelable(false)
                .setView(ed_title);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                now = new Date();
                sDate = formatter.format(now);               // EditText를 통해 입력한 이름으로 채팅방 생성
                sTitle = ed_title.getText().toString();
                menuAdapter.add(new RoomDTO(R.drawable.vec_find, sTitle, "", sDate));
                lv.smoothScrollToPosition(menuAdapter.getCount());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void pressListview() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("userRoom", position);
                editor.commit();
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*  로그아웃  */
    public void logOut() {
        Intent intentLogin = new Intent(MenuActivity.this, LoginActivity.class);
        UtilFunc.UtilClose(intentLogin);
        startActivity(intentLogin);
        Toast.makeText(MenuActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {    // 폰트 설정
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
