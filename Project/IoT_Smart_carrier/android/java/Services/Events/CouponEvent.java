package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.CouponResponse;

/**
 * Created by skCho on 2018-04-03.
 */

public class CouponEvent{
    private CouponResponse couponResponse;

    public CouponEvent(CouponResponse couponResponse) {
        this.couponResponse = couponResponse;
    }

    public CouponResponse getCouponResponse() {
        return couponResponse;
    }

    public void setCouponResponse(CouponResponse couponResponse) {
        this.couponResponse = couponResponse;
    }
}