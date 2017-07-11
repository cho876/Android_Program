package com.example.cho.storage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_name, ed_desc;
    private Button bSave, bImgSave;
    private FirebaseDatabase firebaseDatabase;
    private ImageView imageView;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    /*  View 초기화    */
    public void initView() {
        ed_name = (EditText) findViewById(R.id.name);
        ed_desc = (EditText) findViewById(R.id.desc);
        imageView = (ImageView) findViewById(R.id.image);

        bImgSave = (Button) findViewById(R.id.image_sign);
        bImgSave.setOnClickListener(this);
        bSave = (Button) findViewById(R.id.save);
        bSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_sign:            // 이미지 사진 업로드
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
                break;
            case R.id.save:      // 프로필 Save
                uploadFile();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*  requestCode가 0이고 data 안에 값이 있을 시 */
        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();
            Log.d("MAIN", "uri: " + String.valueOf(filePath));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);  // 이미지 사진 Bitmap화
                imageView.setImageBitmap(bitmap);  // Bitmap으로 변환된 이미지 사진 imageView Setting
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadFile() {
        /*업로드할 파일이 있을 시, */
        if (filePath != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("업로드 중...");
            dialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();

            /* Unique File Name Setting (By Date) */
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String fileName = formatter.format(now);

            /*  FireStorage 주소  및 저장할 root Setting  */
            StorageReference reference = storage.getReferenceFromUrl("gs://chatdb-59f3b.appspot.com").child("images/" + fileName);

            reference.putFile(filePath)
                                         /*  성공 시 */
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            /* FirebaseDatabase 내 이미지, 이름, 상태메세지 저장 */
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            ImageDTO imageDTO = new ImageDTO();
                            imageDTO.ImageUri = filePath.toString();
                            imageDTO.title = ed_name.getText().toString();
                            imageDTO.description = ed_desc.getText().toString();
                            firebaseDatabase.getReference().child("users").setValue(imageDTO);
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "파일 업로드 완료", Toast.LENGTH_SHORT).show();

                        }
                    })
                                        /*  실패 시 */
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "파일 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    })
                                        /*  실행 중일시 */
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        /*업로드할 파일이 없을 시, */
        else {
            Toast.makeText(MainActivity.this, "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
