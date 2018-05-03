package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.SensorResponse;

/**
 * Created by skCho on 2018-04-02.
 */

public class SensorEvent{
    private SensorResponse sensorResponse;

    public SensorEvent(SensorResponse sensorResponse) {
        this.sensorResponse = sensorResponse;
    }

    public SensorResponse getSensorResponse() {
        return sensorResponse;
    }

    public void setSensorResponse(SensorResponse sensorResponse) {
        this.sensorResponse = sensorResponse;
    }
}