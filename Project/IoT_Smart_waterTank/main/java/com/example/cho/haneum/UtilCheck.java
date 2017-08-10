package com.example.cho.haneum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

    public static void UtilClose(Intent intent) {          // 현재 intent를 제외한 모든 intent 삭제
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        else
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static void UtilInternet(final Activity activity) {
        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isConnected() || mobile.isConnected()) {
            Log.i("Connected", "Connnect Internet");
        } else {
            Log.e("Disconneted", "Disconnected Internet");
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("인터넷을 연결해주세요.")
                    .setCancelable(true)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.closeOptionsMenu();
                        }
                    });
        }
    }
}
