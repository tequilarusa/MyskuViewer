package com.iamakulov.myskusdk;

import com.iamakulov.myskusdk.sdk_method_groups.*;

import java.util.Map;

public class MyskuSdk {
    private DocumentRetriever retriever;
    private Map<String, String> cookies;

    public MyskuSdk(DocumentRetriever retriever) {
        this.retriever = retriever;
    }

    public void setAuth(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public ArticlesMethods articles() {
        return new ArticlesMethods(retriever, cookies);
    }

    public ArticleMethods article() {
        return new ArticleMethods(retriever, cookies);
    }

    public UserMethods user() {
        return new UserMethods(retriever, cookies);
    }

    public CommentMethods comment() {
        return new CommentMethods();
    }

    public OtherMethods other() {
        return new OtherMethods();
    }

    public enum TopArticlesInterval {
        DAY,
        WEEK,
        MONTH,
        ALL_TIME
    }

    public enum UserRatingInterval {
        THIS_MONTH,
        PREVIOUS_MONTH
    }

}
