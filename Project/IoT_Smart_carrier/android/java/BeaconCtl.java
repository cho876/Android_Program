package com.example.skcho.smartcarrier;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by skCho on 2017-12-30.
 * <p>
 * Set Becon Class
 */

public class BeaconCtl {
    public static final String uuid = "E2C56DB5-DFFB-48D2-B060-D0F5A71096E0";
    private Context context;
    private Activity activity;
    private BeaconManager beaconManager;
    private Region region;
    private AlertDialog.Builder dialog;

    private boolean isConnected;

    BeaconCtl(Context context, Activity activity, BeaconManager beaconManager) {
        this.context = context;
        this.activity = activity;
        this.beaconManager = beaconManager;
    }

    public void connectBeacon() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString(uuid), 40001, 14903
                ));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.d("들어옴", "비콘 연결됨" + list.get(0).getRssi());
            }

            @Override
            public void onExitedRegion(Region region) {
                Log.d("나감", "비콘 연결끊킴");
            }
        });
    }

    public void setRssi(final TextView tvId) {
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    InsertData task = new InsertData();
                    Beacon nearestBeacon = list.get(0);
                    Log.e("Beacon", "location: " + nearestBeacon.getRssi());
                    tvId.setText(nearestBeacon.getRssi() + "");
                    task.execute(nearestBeacon.getRssi() + "");
                    if (!isConnected && nearestBeacon.getRssi() > -70) {
                        isConnected = true;
                        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                        Log.e("BeaconCtl", "거리가 적당합니다.");
                    } else if (isConnected && nearestBeacon.getRssi() < -80) {
                        dialog = new AlertDialog.Builder(activity);
                        dialog.setTitle("알림")
                                .setMessage("거리가 너무 멀어졌어요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
                    }
                }
            }
        });
        region = new
                Region("ranged region",
                UUID.fromString(uuid), 40001, 14903); // 본인이 연결할 Beacon의 ID와 Major / Minor Code를 알아야 한다.
    }

    class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data;
            String param = "u_rssi=" + params[0] + "";    // 사용자가 기입한 값을 저장한 변수
            try {
                URL url = new URL(
                        "http://192.168.0.6/send_rssi.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                /* 안드로이드 -> 서버 파라메터 값 전달*/
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                InputStream is = null;
                BufferedReader in = null;
                data = "";

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();
                Log.e("RESULT", "MSG - " + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}