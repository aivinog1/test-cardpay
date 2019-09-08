package com.aivinog1.cardpay.convert;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class RequestContainer {
    private final Request request;
    private final Long line;
}
