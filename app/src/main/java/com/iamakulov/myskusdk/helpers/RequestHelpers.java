package com.iamakulov.myskusdk.helpers;

import java.util.Map;

public class RequestHelpers {
    public static String composePostRequestBody(Map<String, String> keyValues) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : keyValues.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return builder.toString();
    }
}
