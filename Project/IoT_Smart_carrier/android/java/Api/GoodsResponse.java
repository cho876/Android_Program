package com.example.skcho.smartcarrier.Api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by skCho on 2018-04-02.
 */

public class GoodsResponse implements Serializable {
    @SerializedName("returned_name")
    private String goodsName;
    @SerializedName("returned_kind")
    private String goodsKind;
    @SerializedName("returned_price")
    private String goodsPrice;
    @SerializedName("response_code")
    private int responseCode;

    public GoodsResponse(String goodName, String goodsKind, String goodsPrice, int responseCode){
        this.goodsName = goodName;
        this.goodsKind = goodsKind;
        this.goodsPrice = goodsPrice;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getGoodsKind() {
        return goodsKind;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsKind(String goodsKind) {
        this.goodsKind = goodsKind;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
