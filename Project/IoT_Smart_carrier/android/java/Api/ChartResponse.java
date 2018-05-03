package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ChartResponse implements Serializable {
    @SerializedName("returned_category")
    private String category;
    @SerializedName("returned_count")
    private String count;
    @SerializedName("response_code")
    private int responseCode;

    public ChartResponse(String category, String count, int responseCode){
        this.category = category;
        this.count = count;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
