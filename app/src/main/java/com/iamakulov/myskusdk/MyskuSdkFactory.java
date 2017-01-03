package com.iamakulov.myskusdk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;
import java.util.function.BiFunction;

public class MyskuSdkFactory {
    public static MyskuSdk createSdk() {
        return new MyskuSdk(new RealDocumentRetriever());
    }

    public static MyskuSdk createSdkWithCustomRetriever(DocumentRetriever retriever) {
        return new MyskuSdk(retriever);
    }
}
