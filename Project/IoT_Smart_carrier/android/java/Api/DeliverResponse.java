package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by skCho on 2018-04-03.
 */

public class DeliverResponse implements Serializable {
    @SerializedName("returned_name")
    private String username;
    @SerializedName("returned_address")
    private String address;
    @SerializedName("returned_flag")
    private int flag;
    @SerializedName("response_code")
    private int responseCode;

    public DeliverResponse(String address, int responseCode) {
        this.address = address;
        this.responseCode = responseCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
