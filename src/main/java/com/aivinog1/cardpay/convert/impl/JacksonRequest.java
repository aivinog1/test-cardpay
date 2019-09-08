package com.aivinog1.cardpay.convert.impl;

import com.aivinog1.cardpay.convert.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonPropertyOrder({"orderId", "amount", "currency", "comment"})
@Getter
public class JacksonRequest implements Request {
    private final Long orderId;
    private final Long amount;
    private final String currency;
    private final String comment;

    public JacksonRequest(@JsonProperty(value = "orderId", index = 0) Long orderId,
                          @JsonProperty(value = "amount", index = 1) Long amount,
                          @JsonProperty(value = "currency", index = 2) String currency,
                          @JsonProperty(value = "comment", index = 3) String comment) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }
}
