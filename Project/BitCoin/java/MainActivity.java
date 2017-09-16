package com.example.cho.bitcoin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ButtonCustom bc_btc, bc_eth, bc_dash, bc_xrp, bc_ltc, bc_etc, bc_bch, bc_xmr, bc_qtum;
    private TitlebarCustom titlebarCustom;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ConvertToJsonThread toJsonThread;
    private RegisterRequest registerRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("Bitcoin", MODE_PRIVATE);
        editor = pref.edit();

        initTitlebar();
        initButtonCustom();
    }

    private void initTitlebar() {
        titlebarCustom = new TitlebarCustom(this);
        ActionBar actionBar = getSupportActionBar();
        titlebarCustom.setActionBar(actionBar);
    }

    private void initButtonCustom() {
        bc_btc = (ButtonCustom) findViewById(R.id.main_custombutton_btc);
        bc_btc.setOnClickListener(this);
        bc_eth = (ButtonCustom) findViewById(R.id.main_custombutton_eth);
        bc_eth.setOnClickListener(this);
        bc_dash = (ButtonCustom) findViewById(R.id.main_custombutton_dash);
        bc_dash.setOnClickListener(this);
        bc_xrp = (ButtonCustom) findViewById(R.id.main_custombutton_xrp);
        bc_xrp.setOnClickListener(this);
        bc_ltc = (ButtonCustom) findViewById(R.id.main_custombutton_ltc);
        bc_ltc.setOnClickListener(this);
        bc_etc = (ButtonCustom) findViewById(R.id.main_custombutton_etc);
        bc_etc.setOnClickListener(this);
        bc_bch = (ButtonCustom) findViewById(R.id.main_custombutton_bch);
        bc_bch.setOnClickListener(this);
        bc_xmr = (ButtonCustom) findViewById(R.id.main_custombutton_xmr);
        bc_xmr.setOnClickListener(this);
        bc_qtum = (ButtonCustom) findViewById(R.id.main_custombutton_qtum);
        bc_qtum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_custombutton_btc:
                editor.putString("exchange", "btc");
                editor.commit();
                Intent go_btc = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_btc);
                break;
            case R.id.main_custombutton_eth:
                editor.putString("exchange", "eth");
                editor.commit();
                Intent go_eth = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_eth);
                break;
            case R.id.main_custombutton_dash:
                editor.putString("exchange", "dash");
                editor.commit();
                Intent go_dash = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_dash);
                break;
            case R.id.main_custombutton_xrp:
                editor.putString("exchange", "xrp");
                editor.commit();
                Intent go_xrp = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_xrp);
                break;
            case R.id.main_custombutton_ltc:
                editor.putString("exchange", "ltc");
                editor.commit();
                Intent go_ltc = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_ltc);
                break;
            case R.id.main_custombutton_etc:
                editor.putString("exchange", "etc");
                editor.commit();
                Intent go_etc = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_etc);
                break;
            case R.id.main_custombutton_bch:
                editor.putString("exchange", "bch");
                editor.commit();
                Intent go_bch = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_bch);
                break;
            case R.id.main_custombutton_xmr:
                editor.putString("exchange", "xmr");
                editor.commit();
                Intent go_xmr = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_xmr);
                break;
            case R.id.main_custombutton_qtum:
                editor.putString("exchange", "qtum");
                editor.commit();
                Intent go_qtum = new Intent(getApplicationContext(), InformationActivity.class);
                startActivity(go_qtum);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
