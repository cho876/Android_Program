package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.GoodsResponse;

import java.util.List;

/**
 * Created by skCho on 2018-04-03.
 */

public class GoodsListEvent {
    private List<GoodsResponse> goodsResponseList;

    public GoodsListEvent(List<GoodsResponse> goodsResponseList) {
        this.goodsResponseList = goodsResponseList;
    }

    public List<GoodsResponse> getGoodsResponse() {
        return goodsResponseList;
    }

    public void setGoodsResponse(List<GoodsResponse> goodsResponseList) {
        this.goodsResponseList = goodsResponseList;
    }
}