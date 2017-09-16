package com.example.cho.bitcoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class HuobiFragment extends Fragment {

    private TextView tv_exchange, tv_kr_max, tv_us_max, tv_kr_min, tv_us_min,
            tv_kr_change, tv_us_change, tv_kr_premium_change, tv_us_premium_change,
            tv_percent, tv_premium_percent, tv_volume;
    private ConvertToJsonThread toJsonThread;
    private RegisterRequest registerRequest;
    private JSONObject jsonObject;
    private Handler handler;
    private SharedPreferences pref;
    private String exchangeName;
    private Data data;
    private FormatPackage formatPackage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pref = this.getContext().getSharedPreferences("Bitcoin", Context.MODE_PRIVATE);
        exchangeName = pref.getString("exchange", null);
        View rootView = inflater.inflate(R.layout.fragment_huobi, container, false);
        initView(rootView);
        initHandler();
        joinThread(handler);
        return rootView;
    }

    private void initView(View view) {
        tv_exchange = (TextView) view.findViewById(R.id.huobi_exchange);

        tv_kr_max = (TextView) view.findViewById(R.id.huobi_kr_max);
        tv_us_max = (TextView) view.findViewById(R.id.huobi_us_max);

        tv_kr_min = (TextView) view.findViewById(R.id.huobi_kr_min);
        tv_us_min = (TextView) view.findViewById(R.id.huobi_us_min);

        tv_kr_change = (TextView) view.findViewById(R.id.huobi_kr_change);
        tv_us_change = (TextView) view.findViewById(R.id.huobi_us_change);
        tv_percent = (TextView) view.findViewById(R.id.huobi_percent);

        tv_kr_premium_change = (TextView) view.findViewById(R.id.huobi_kr_premium_change);
        tv_us_premium_change = (TextView) view.findViewById(R.id.huobi_us_premium_change);
        tv_premium_percent = (TextView) view.findViewById(R.id.huobi_premium_percent);

        tv_volume = (TextView) view.findViewById(R.id.huobi_volume);
    }

    private void initHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    jsonObject = (JSONObject) msg.obj;
                    data = new Data(jsonObject);
                    formatPackage = new FormatPackage();
                    formatPackage.setText(tv_exchange, data.getExchange());
                    formatPackage.setTextKorea(tv_kr_max, data.getKoreaMax());
                    formatPackage.setTextUsa(tv_us_max, data.getUsaMax());
                    formatPackage.setTextKorea(tv_kr_min, data.getKoreaMin());
                    formatPackage.setTextUsa(tv_us_min, data.getUsaMin());
                    formatPackage.setTextKorea(tv_kr_premium_change, data.getKoreaPremium());
                    formatPackage.setTextUsa(tv_us_premium_change, data.getUsaPremium());
                    formatPackage.setTextPercent(tv_percent, data.getPercent());
                    formatPackage.setTextPercent(tv_premium_percent, data.getPremiumPercent());
                    formatPackage.setTextKorea(tv_kr_change, data.getKoreaChangeRate());
                    formatPackage.setTextUsa(tv_us_change, data.getUsaChangeRate());
                    formatPackage.setTextVolume(tv_volume, data.getVolume());
                }
            }
        };
    }

    private void joinThread(Handler handler) {
        toJsonThread = new ConvertToJsonThread(getContext(), registerRequest, handler);
        toJsonThread.setCompany(exchangeName);
        toJsonThread.setTitle("huobi");
        toJsonThread.setDaemon(true);
        toJsonThread.run();
    }
}

