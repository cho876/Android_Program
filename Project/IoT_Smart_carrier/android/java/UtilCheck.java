package com.example.skcho.smartcarrier;


import android.content.Intent;
import android.os.Build;
import android.support.v4.content.IntentCompat;
/**
 * 액티비티 간 스택 처리
 */

public class UtilCheck {            // EditText 창에 미 입력칸 존재 시, false

    public static void UtilClose(Intent intent) {          // 현재 intent를 제외한 모든 intent 삭제
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}