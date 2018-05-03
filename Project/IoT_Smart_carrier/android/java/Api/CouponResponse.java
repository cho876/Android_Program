package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by skCho on 2018-04-03.
 */

public class CouponResponse implements Serializable {
    @SerializedName("returned_kind")
    private String kind;
    @SerializedName("returned_description")
    private String description;
    @SerializedName("response_code")
    private int responseCode;

    public CouponResponse(String kind, String description, int responseCode){
        this.kind = kind;
        this.description = description;
        this.responseCode = responseCode;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }
}
