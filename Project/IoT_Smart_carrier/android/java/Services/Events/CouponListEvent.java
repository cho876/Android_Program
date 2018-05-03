package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.CouponResponse;

import java.util.List;

/**
 * Created by skCho on 2018-04-03.
 */

public class CouponListEvent {
    private List<CouponResponse> couponResponseList;

    public CouponListEvent(List<CouponResponse> couponResponseList) {
        this.couponResponseList = couponResponseList;
    }

    public List<CouponResponse> getCouponResponseList() {
        return couponResponseList;
    }

    public void setCouponResponseList(List<CouponResponse> couponResponseList) {
        this.couponResponseList = couponResponseList;
    }
}