package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.DeliverResponse;

import java.util.List;

/**
 * Created by skCho on 2018-04-03.
 */

public class DeliveryListEvent {
    private List<DeliverResponse> deliverResponseList;

    public DeliveryListEvent(List<DeliverResponse> deliverResponseList) {
        this.deliverResponseList = deliverResponseList;
    }

    public List<DeliverResponse> getDeliverResponseList() {
        return deliverResponseList;
    }

    public void setDeliverResponseList(List<DeliverResponse> deliverResponseList) {
        this.deliverResponseList = deliverResponseList;
    }
}