package com.example.cho.haneum;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Cho on 2017-06-18.
 */

public class UtilCheck {            // EditText 창에 미 입력칸 존재 시, false
    public static boolean isChecked(String data) {
        if (data.equals(""))
            return false;
        else
            return true;
    }

    public static void UtilClose(Intent intent) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
