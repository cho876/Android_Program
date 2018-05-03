package com.example.skcho.smartcarrier.Services.Event;

import com.example.skcho.smartcarrier.Api.DeliverResponse;

/**
 * Created by skCho on 2018-04-03.
 */

public class DeliveryEvent {
    private DeliverResponse deliverResponse;

    public DeliveryEvent(DeliverResponse deliverResponse) {
        this.deliverResponse = deliverResponse;
    }

    public DeliverResponse getDeliverResponse() {
        return deliverResponse;
    }

    public void setDeliverResponse(DeliverResponse deliverResponse) {
        this.deliverResponse = deliverResponse;
    }
}