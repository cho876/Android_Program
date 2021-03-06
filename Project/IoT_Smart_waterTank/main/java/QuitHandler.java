package com.example.cho.haneum;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class QuitHandler {                     // 프로그램 종료 Handler

    public static Activity activity;
    private long backKeyPressedTime = 0;
    private Toast toast;

    public QuitHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            ActivityCompat.finishAffinity(activity);
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
