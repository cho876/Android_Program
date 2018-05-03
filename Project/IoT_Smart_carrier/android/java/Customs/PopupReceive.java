package com.example.skcho.smartcarrier.Customs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Activities.MainActivity;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Utils.TransitionsMng;


/**
 * Created by skCho on 2018-03-06.
 */

public class PopupReceive extends Dialog implements View.OnClickListener {
    private TextView txt_content;
    private Button btn_check;
    private Context context;

    private WindowManager.LayoutParams layoutParams;

    public PopupReceive(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_receive);

        init();
    }

    private void init() {
        txt_content = (TextView) findViewById(R.id.pop_rcv_content);
        txt_content.setText("쿠폰이 도착했습니다.\n지금 바로 확인해보세요!!!");
        btn_check = (Button) findViewById(R.id.pop_rcv_btn);
        btn_check.setOnClickListener(this);

        layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
        Intent go_bt = new Intent(context, MainActivity.class);
        TransitionsMng.UtilClose(go_bt);
        context.startActivity(go_bt);
        cancel();
    }
}