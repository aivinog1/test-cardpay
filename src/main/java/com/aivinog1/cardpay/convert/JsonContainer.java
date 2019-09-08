package com.aivinog1.cardpay.convert;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class JsonContainer {
    private final JsonRequest jsonRequest;
    private final Long line;
}
