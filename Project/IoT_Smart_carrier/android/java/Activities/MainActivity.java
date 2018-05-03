package com.example.skcho.smartcarrier.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.example.skcho.smartcarrier.BeaconCtl;
import com.example.skcho.smartcarrier.Customs.CustomTable;
import com.example.skcho.smartcarrier.FabUtils;
import com.example.skcho.smartcarrier.Services.Event.DeliveryEvent;
import com.example.skcho.smartcarrier.Services.Service.GoodsService;
import com.example.skcho.smartcarrier.Utils.QuitHandler;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.SensorEvent;
import com.example.skcho.smartcarrier.Services.Service.SensorService;
import com.example.skcho.smartcarrier.Services.Event.UserEvent;
import com.example.skcho.smartcarrier.Services.Service.UserService;
import com.example.skcho.smartcarrier.Utils.TransitionsMng;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserService userService;
    private SensorService sensorService;
    private GoodsService goodsService;

    private TextView tv_weight, tv_distance_left, tv_distance_right, tv_delivery;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor;
    private BeaconManager beaconManager;
    private ProgressDialog dialog;
    private CustomTable customTable;
    private FloatingActionButton fb_on, fb_map, fb_graph, fb_coupon;
    private Button btn_logout, btn_pay;
    private FabUtils fabUtils;
    private QuitHandler quitHandler;
    boolean isOpen = false;
    private String sId, sWeight, sDelivery;

    public BeaconCtl beaconCtl;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), "자동 모드 실행 중", Toast.LENGTH_SHORT).show();
        quitHandler = new QuitHandler(this);

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    receiveSensor();
                    try {
                        Thread.sleep(1000);
                        dialog.dismiss();
                    } catch (InterruptedException e) {
                        Log.e("InterruptError", "Error Occured");
                    }
                }
            }
        }).start();

        /*beaconManager = new BeaconManager(this);
        beaconCtl = new BeaconCtl(this.getApplicationContext(), beaconManager);
        beaconCtl.setRssi(tv_distance_left, tv_distance_right);*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_logout:
                logoutByPost();
                break;

            case R.id.main_pay:
                Intent go_pay = new Intent(this, BucketListActivity.class);
                startActivity(go_pay);
                Toast.makeText(getApplicationContext(), "결제 창으로 이동합니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void logoutByPost() {
        userService.logoutById("logout", sId);
    }

    public void receiveSensor() {
        sensorService.getSensorValue();
    }

    public void deliverByPost() {
        goodsService.deliverGoodsById("deliver", sId);
    }

    @Subscribe
    public void onServerEvent(UserEvent userEvent) {
        if (!userEvent.getUserResponse().getUsername().equals("EMPTY")) {
            editor.putBoolean("Status", false);
            editor.putBoolean("Alarm", false);
            editor.commit();
            flag = false;
            Intent go_login = new Intent(this, LoginActivity.class);
            TransitionsMng.UtilClose(go_login);
            startActivity(go_login);
            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onServerEvent(SensorEvent sensorEvent) {
        Log.e("TAB", "www: " + sensorEvent.getSensorResponse().getResponseCode());
        if (sensorEvent.getSensorResponse().getResponseCode() == 1) {
            sWeight = sensorEvent.getSensorResponse().getWeight();

            tv_weight.setText(sWeight);

            if(sensorEvent.getSensorResponse().getDelivery_flag() == 1)
                tv_delivery.setText("활성");
            else
                tv_delivery.setText("비활성");

            deliverService(sWeight);
        }
    }

    @Subscribe
    public void onServerEvent(DeliveryEvent deliveryEvent) {
        if (deliveryEvent.getDeliverResponse().getResponseCode() == 1) {
            Toast.makeText(getApplicationContext(), "배송 서비스가 접수되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        editor = pref.edit();
        editor.putBoolean("Alarm", false);  // 배송 서비스 알람 (1번 로그인 시, 1회 알람 서비스 제공)
        editor.commit();
        sId = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력

        userService = new UserService();
        sensorService = new SensorService();
        goodsService = new GoodsService();

        btn_logout = (Button) findViewById(R.id.main_logout);
        btn_logout.setOnClickListener(this);
        btn_pay = (Button) findViewById(R.id.main_pay);
        btn_pay.setOnClickListener(this);

        fb_on = (FloatingActionButton) findViewById(R.id.view_float_on);
        fb_map = (FloatingActionButton) findViewById(R.id.view_float_map);
        fb_graph = (FloatingActionButton) findViewById(R.id.view_float_graph);
        fb_coupon = (FloatingActionButton) findViewById(R.id.view_float_coupon);

        fabUtils = new FabUtils(this, fb_on, fb_map, fb_graph, fb_coupon, isOpen);
        fabUtils.clickFab();

        dialog = new ProgressDialog(this);
        dialog.setMessage("잠시만 기다려 주세요...");
        customTable = (CustomTable) findViewById(R.id.custom_main_weight);
        tv_weight = customTable.getTv_contents();

        customTable = (CustomTable) findViewById(R.id.custom_main_distance_left);
        tv_distance_left = customTable.getTv_contents();

        customTable = (CustomTable) findViewById(R.id.custom_main_distance_right);
        tv_distance_right = customTable.getTv_contents();

        customTable = (CustomTable)findViewById(R.id.custom_main_delivery);
        tv_delivery = customTable.getTv_contents();
    }

    private void deliverService(String weight) {
        int conv_weight = Integer.parseInt(weight);
        boolean isAlarmed = pref.getBoolean("Alarm", false);

        if (conv_weight > 10 && !isAlarmed) {
            editor.putBoolean("Alarm", true);
            editor.commit();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("배송 서비스 알람")        // 제목 설정
                    .setMessage("배송 서비스를 접수하시겠습니까?")        // 메세지 설정
                    .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deliverByPost();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();    // 알림창 객체 생성
            dialog.show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        quitHandler.onBackPressed();
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}