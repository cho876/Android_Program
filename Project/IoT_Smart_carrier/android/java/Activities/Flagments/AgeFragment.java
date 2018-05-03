package com.example.skcho.smartcarrier.Activities.Flagments;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.skcho.smartcarrier.Api.ChartResponse;
import com.example.skcho.smartcarrier.Activities.MasterChartActivity;
import com.example.skcho.smartcarrier.Customs.PopupSend;
import com.example.skcho.smartcarrier.R;
import com.example.skcho.smartcarrier.Services.BusProvider;
import com.example.skcho.smartcarrier.Services.Event.ChartListEvent;
import com.example.skcho.smartcarrier.Services.Service.ChartService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class AgeFragment extends Fragment implements OnChartValueSelectedListener {

    private ChartService chartService;
    private PopupSend popupSend;

    private MasterChartActivity masterChartActivity;

    private float[] yData;
    private String[] xData;
    private PieChart pieChart;

    private String s_category, s_count;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        masterChartActivity = (MasterChartActivity) getActivity();//activity 시작
    }

    @Override
    public void onDetach() {
        super.onDetach();
        masterChartActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_age, container, false);

        init(rootView);

        getAgeChartList();
        return rootView;
    }

    private void init(ViewGroup rootView) {
        chartService = new ChartService();

        pieChart = (PieChart) rootView.findViewById(R.id.PieChart);

        pieChart.setHoleRadius(25f);//차트 가운데 흰 원의 크기
        pieChart.setTransparentCircleAlpha(0);//음영?
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);//chart안에 종류 이름 나오게
        pieChart.getDescription().setEnabled(false);//label 없애줌
        pieChart.setOnChartValueSelectedListener(this);

        yData = new float[5];
        xData = new String[5];
    }

    private void getAgeChartList() {
        chartService.getAllUserByAge();
    }

    @Subscribe
    public void onServerEvent(ChartListEvent chartListEvent) {
        if (chartListEvent.getChartResponse().get(0).getResponseCode() == -1)
            Toast.makeText(masterChartActivity, "차트를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
        else {
            List<ChartResponse> goodsResponseList = chartListEvent.getChartResponse();
            putItems(goodsResponseList);
            Toast.makeText(masterChartActivity, "차트를 불러왔습니다.", Toast.LENGTH_SHORT).show();
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
            s_category = chartResponse.getCategory();
            s_count = chartResponse.getCount();
            xData[i] = s_category;
            yData[i] = Integer.parseInt(s_count);
            Log.e("put", s_category + ", " + s_count);
            i++;
        }
        addDataSet();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        final int a = (int) h.getX();
        popupSend = new PopupSend(masterChartActivity, "age", xData[a]);
        popupSend.show();
    }

    @Override
    public void onNothingSelected() {
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
}
