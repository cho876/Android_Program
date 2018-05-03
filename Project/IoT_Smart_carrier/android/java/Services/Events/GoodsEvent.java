package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.GoodsResponse;



public class GoodsEvent {
    private GoodsResponse goodsResponse;

    public GoodsEvent(GoodsResponse goodsResponse) {
        this.goodsResponse = goodsResponse;
    }

    public GoodsResponse getGoodsResponse() {
        return goodsResponse;
    }

    public void setGoodsResponse(GoodsResponse goodsResponse) {
        this.goodsResponse = goodsResponse;
    }
}