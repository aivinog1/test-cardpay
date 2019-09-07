package com.aivinog1.cardpay.parse.json.impl;

import com.aivinog1.cardpay.convert.JsonType;
import com.aivinog1.cardpay.convert.Response;
import com.aivinog1.cardpay.parse.json.JsonParser;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ListOfJsonsJsonParser implements JsonParser {

    /**
     * @param lines lines from a json file
     * @return list of the response.
     * @todo #18:30 Let's implement this method. It should parse separate lines as separate jsons.
     */
    @Override
    public List<Response> parsed(Collection<String> lines) {
        return Collections.singletonList(new Response(1L, 1L, "comment", "filename", 1L, "OK"));
    }

    @Override
    public JsonType getType() {
        return JsonType.LIST_OF_OBJECT;
    }
}
