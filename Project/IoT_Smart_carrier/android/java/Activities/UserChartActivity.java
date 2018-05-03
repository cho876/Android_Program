package com.example.skcho.smartcarrier.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Api.ChartResponse;
import com.example.skcho.smartcarrier.Api.GoodsResponse;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.ChartListEvent;
import com.example.skcho.smartcarrier.Services.Event.UserEvent;
import com.example.skcho.smartcarrier.Services.Service.ChartService;
import com.example.skcho.smartcarrier.Utils.TransitionsMng;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.squareup.otto.Subscribe;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserChartActivity extends AppCompatActivity {

    private ChartService chartService;
    private SharedPreferences pref = null;

    private float yData[];
    private String xData[];
    PieChart pieChart;
    String s_user;
    String s_category;
    String s_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chart);

        init();
        getUserChartList();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void init() {
        pref = getSharedPreferences("settingDB", MODE_PRIVATE);
        s_user = pref.getString("Id", "");  // 기존에 저장되어있던 ID 불러와 출력

        chartService = new ChartService();

        xData = new String[5];
        yData = new float[5];

        pieChart = (PieChart) findViewById(R.id.PieChart);
        pieChart.setHoleRadius(25f);//차트 가운데 흰 원의 크기
        pieChart.setTransparentCircleAlpha(2);//음영?
        pieChart.setCenterTextSize(7);
        pieChart.setDrawEntryLabels(true);//chart안에 종류 이름 나오게
        pieChart.getDescription().setEnabled(false);//label 없애줌

    }

    private void getUserChartList() {
        chartService.getUserChartById(s_user);
    }

    @Subscribe
    public void onServerEvent(ChartListEvent chartListEvent) {
        if (chartListEvent.getChartResponse().get(0).getResponseCode() == -1)
            Toast.makeText(getApplicationContext(), "차트를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        else {
            List<ChartResponse> goodsResponseList = chartListEvent.getChartResponse();
            putItems(goodsResponseList);
            Toast.makeText(getApplicationContext(), "차트를 불러왔습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);//종목별 빈칸 크기
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();//색깔 추가
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();//범주의 모양과 위치를 설정
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void putItems(List<ChartResponse> data) {
        int i = 0;
        for (ChartResponse chartResponse : data) {
            s_category =chartResponse.getCategory();
            s_count = chartResponse.getCount();
            xData[i] = s_category;
            yData[i] = Integer.parseInt(s_count);
            Log.e("put", s_category+", " + s_count);
            i++;
        }
        addDataSet();
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}