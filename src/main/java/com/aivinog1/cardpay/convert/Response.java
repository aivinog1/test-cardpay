package com.aivinog1.cardpay.convert;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@RequiredArgsConstructor
@Getter
public class Response {
    private final Long id;
    private final Long amount;
    private final String comment;
    private final String filename;
    private final Long line;
    private final String result;
}
