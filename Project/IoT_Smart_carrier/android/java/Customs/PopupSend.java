package com.example.skcho.smartcarrier.Customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Api.ChartResponse;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.Event.ChartListEvent;
import com.example.skcho.smartcarrier.Services.Event.CouponEvent;
import com.example.skcho.smartcarrier.Services.Service.CouponService;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by skCho on 2018-03-06.
 */

public class PopupSend extends Dialog implements View.OnClickListener {

    private CouponService couponService;

    private EditText edit_content;
    private String method, xData;
    private Button btn_check;
    private Context context;

    private WindowManager.LayoutParams layoutParams;

    public PopupSend(Context context, String method, String xData) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.method = method;
        this.xData = xData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_send);

        init();
    }

    private void init() {
        couponService = new CouponService();

        edit_content = (EditText) findViewById(R.id.pop_snd_edit);
        btn_check = (Button) findViewById(R.id.pop_snd_btn);
        btn_check.setOnClickListener(this);

        layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        sendCoupon(edit_content.getText().toString());
        cancel();
    }

    private void sendCoupon(String desc) {
        couponService.sendCoupon(method, xData, desc);
    }

    @Subscribe
    public void onServerEvent(CouponEvent couponEvent) {
        if (couponEvent.getCouponResponse().getResponseCode() == -1)
            Toast.makeText(context, "차트를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "차트를 불러왔습니다.", Toast.LENGTH_SHORT).show();
    }
}
