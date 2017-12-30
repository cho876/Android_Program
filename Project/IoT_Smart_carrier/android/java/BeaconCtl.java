package com.example.skcho.smartcarrier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

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
                    Beacon nearestBeacon = list.get(0);
                    Log.e("Beacon", "location: " + nearestBeacon.getRssi());
                    tvId.setText(nearestBeacon.getRssi() + "");
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
}
