package com.aivinog1.cardpay.parse.json;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Response;

import java.util.List;

public interface JsonParser {

    List<Response> parsed(List<String> lines);

    JsonType getType();
}
