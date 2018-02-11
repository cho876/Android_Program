package com.example.skcho.smartcarrier;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;
import java.util.UUID;

import static com.example.skcho.smartcarrier.BeaconCtl.uuid;


public class MainActivity extends AppCompatActivity {

    private TextView tv_weight, tv_distance, tv_speed;

    private BeaconManager beaconManager;
    private ProgressDialog dialog;
    private RetrofitConnect retrofitConnect;
    private CustomTable customTable;
    private FloatingActionButton fb_on, fb_map, fb_memo, fb_manual;
    private FabUtils fabUtils;
    private QuitHandler quitHandler;
    boolean isOpen = false;

    public BeaconCtl beaconCtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "자동 모드 실행 중", Toast.LENGTH_SHORT).show();

        beaconManager = new BeaconManager(this);
        beaconCtl = new BeaconCtl(this.getApplicationContext(), MainActivity.this, beaconManager);
        retrofitConnect = new RetrofitConnect();
        quitHandler = new QuitHandler(this);

        initView();
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    connectToDB();
                    try {
                        Thread.sleep(1000);
                        dialog.dismiss();
                    } catch (InterruptedException e) {
                        Log.e("InterruptError", "Error Occured");
                    }
                }
            }
        }).start();

        beaconCtl.connectBeacon();
        beaconCtl.setRssi(tv_distance);
    }

    private void initView() {
        fb_on = (FloatingActionButton) findViewById(R.id.view_float_on);
        fb_map = (FloatingActionButton) findViewById(R.id.view_float_map);
        fb_memo = (FloatingActionButton) findViewById(R.id.view_float_memo);
        fb_manual = (FloatingActionButton) findViewById(R.id.view_float_manual);

        fabUtils = new FabUtils(this, fb_on, fb_map, fb_memo, fb_manual, isOpen);
        fabUtils.clickFab();

        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려 주세요...");
        customTable = (CustomTable) findViewById(R.id.custom_main_weight);
        tv_weight = customTable.getTv_contents();

        customTable = (CustomTable) findViewById(R.id.custom_main_distance);
        tv_distance = customTable.getTv_contents();

        customTable = (CustomTable) findViewById(R.id.custom_main_speed);
        tv_speed = customTable.getTv_contents();
    }

    /**
     */
    private void connectToDB() {
        retrofitConnect.getDataByAsync(new RetrofitConnect.callBack<CartDAO>() {
            @Override
            public void execute(CartDAO data) {
                tv_weight.setText(data.getWeight() + " (kg)");
                //tv_distance.setText(data.getDistance() + " (cm)");
                tv_speed.setText(data.getSpeed());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging((new Region(
                        "monitored region",
                        UUID.fromString(uuid), 40001, 14903)
                ));
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    /**
     */
    @Override
    public void onBackPressed() {
        quitHandler.onBackPressed();
    }

    /**
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}