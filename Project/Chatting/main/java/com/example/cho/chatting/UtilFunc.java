package com.example.cho.chatting;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.IntentCompat;

/**
 * Created by Cho on 2017-07-13.
 */

public class UtilFunc {
    public static void UtilClose(Intent intent) {          // 현재 intent를 제외한 모든 intent 삭제
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
