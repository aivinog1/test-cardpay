package com.aivinog1.cardpay.convert;

public interface Request {
    Long getOrderId();
    Long getAmount();
    String getCurrency();
    String getComment();
}
