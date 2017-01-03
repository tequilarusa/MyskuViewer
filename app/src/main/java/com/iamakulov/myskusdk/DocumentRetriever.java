package com.iamakulov.myskusdk;

import java.util.Map;
import org.jsoup.nodes.Document;

public interface DocumentRetriever {
    void get(String url, Map<String, String> cookies, MyskuCallback<Document> callback);

    void post(String url, String body, Map<String, String> cookies, MyskuCallback<Document> callback);
}
