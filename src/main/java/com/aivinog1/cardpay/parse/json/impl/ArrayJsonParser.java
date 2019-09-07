package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.parse.json.JsonParser;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ArrayJsonParser implements JsonParser {

    /**
     * @param lines lines, that contains json
     * @return parsed list of objects
     * @todo #18:30m Needs to implement this method. It should parse lines as single JSON array.
     */
    @Override
    public List<Response> parsed(Collection<String> lines) {
        return Collections.singletonList(new Response(1L, 1L, "comment", "filename", 1L, "OK"));
    }

    @Override
    public JsonType getType() {
        return JsonType.ARRAY;
    }
}
