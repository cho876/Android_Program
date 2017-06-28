package com.example.cho.accountbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
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
 * Created by Cho on 2017-06-28.
 */

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";
    String sMsg = null;
    String sNumber = null;

    @Override
    public void onReceive(Context context, Intent intent) {      // 메세지 수신 시, 호출되는 콜백 메서드
        Bundle bundle = intent.getExtras();    // SMS message Parshing


        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");  // Object 배열에 PDU 형식으로 저장

            SmsMessage[] msgs = new SmsMessage[pdus.length];

            // PDU 포맷으로 되어있는 메세지 복원
            msgs[0] = SmsMessage.createFromPdu((byte[]) pdus[0]);
            sMsg = msgs[0].getMessageBody().toString();
            sNumber = msgs[0].getDisplayOriginatingAddress().toString();

            Log.e("NUM", sNumber);
            Log.e("MSG", sMsg);
            MemPHP memPHP = new MemPHP();
            memPHP.execute();
            Toast.makeText(context, "저장되었습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public class MemPHP extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... unused) {
            String param = "u_number=" + sNumber + "&u_msg=" + sMsg + "";
            try {
                URL url = new URL(
                        "http://211.253.25.169/mJoin.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                InputStream is = null;
                BufferedReader in = null;
                String data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null)
                    buff.append(line + "\n");
                data = buff.toString().trim();

                if (data.equals("1"))
                    Log.e("RESULT: ", "Success");
                else
                    Log.e("RESULT: ", "Fail" + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
