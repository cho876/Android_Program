package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.ChartResponse;

import java.util.List;

/**
 * Created by skCho on 2018-04-03.
 */

public class ChartListEvent {
    private List<ChartResponse> chartResponse;

    public ChartListEvent(List<ChartResponse> chartResponse) {
        this.chartResponse = chartResponse;
    }

    public List<ChartResponse> getChartResponse() {
        return chartResponse;
    }

    public void setChartResponse(List<ChartResponse> goodsResponse) {
        this.chartResponse = chartResponse;
    }
}