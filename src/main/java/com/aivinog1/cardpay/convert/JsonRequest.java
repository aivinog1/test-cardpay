package com.aivinog1.cardpay.convert;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class JsonRequest {
    private final Long orderId;
    private final Long amount;
    private final String currency;
    private final String comment;

    public JsonRequest(@JsonProperty("orderId") Long orderId,
                       @JsonProperty("amount") Long amount,
                       @JsonProperty("currency") String currency,
                       @JsonProperty("comment") String comment) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }
}
