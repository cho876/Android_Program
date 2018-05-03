package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by skCho on 2018-04-02.
 */

public class SensorResponse implements Serializable {
    @SerializedName("returned_weight")
    private String weight;
    @SerializedName("returned_distance")
    private String distance;
    @SerializedName("returned_delivery")
    private int delivery_flag;
    @SerializedName("returned_rssi")
    private String rssi;
    @SerializedName("response_code")
    private int responseCode;

    public SensorResponse(String weight, String distance, int delivery_flag, String rssi, int responseCode){
       this.weight =weight;
       this.distance = distance;
       this.delivery_flag = delivery_flag;
       this.rssi = rssi;
       this.responseCode = responseCode;
    }

    public int getDelivery_flag() {
        return delivery_flag;
    }

    public void setDelivery_flag(int delivery_flag) {
        this.delivery_flag = delivery_flag;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getDistance() {
        return distance;
    }

    public String getRssi() {
        return rssi;
    }

    public String getWeight() {
        return weight;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
