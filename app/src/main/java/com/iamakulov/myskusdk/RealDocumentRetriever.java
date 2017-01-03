package com.iamakulov.myskusdk;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RealDocumentRetriever implements DocumentRetriever {
    @Override
    public void get(String url, Map<String, String> cookies, MyskuCallback<Document> callback) {
        RequestTaskParams params = new RequestTaskParams(
                "GET",
                url,
                cookies,
                callback
        );

        new RequestTask().execute(params);
    }

    @Override
    public void post(String url, String body, Map<String, String> cookies, MyskuCallback<Document> callback) {
        RequestTaskParams params = new RequestTaskParams(
                "POST",
                url,
                "text/html",
                body,
                cookies,
                callback
        );

        new RequestTask().execute(params);
    }

    private static class RequestTask extends AsyncTask<RequestTaskParams, Void, Void> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected Void doInBackground(RequestTaskParams... paramsList) {
            RequestTaskParams params = paramsList[0];

            try {
                Request.Builder requestBuilder = new Request.Builder().url(params.url);
                if (params.mediaType != null && params.body != null) {
                    requestBuilder.method(
                            params.method,
                            RequestBody.create(MediaType.parse(params.mediaType), params.body)
                    );
                }
                Request request = requestBuilder.build();
                Response response = client.newCall(request).execute();

                String content = response.body().string();
                Document document = Jsoup.parse(content);

                params.callback.onSuccess(document);
            } catch (IOException e) {
                params.callback.onError(new MyskuError(e));
            }

            return null;
        }
    }

    private static class RequestTaskParams {
        String method;
        String url;
        String mediaType;
        String body; // Used only in supported methods
        Map<String, String> cookies;
        MyskuCallback<Document> callback;

        RequestTaskParams(String method, String url, String mediaType, String body, Map<String, String> cookies, MyskuCallback<Document> callback) {
            this.method = method;
            this.url = url;
            this.mediaType = mediaType;
            this.body = body;
            this.cookies = cookies;
            this.callback = callback;
        }

        RequestTaskParams(String method, String url, Map<String, String> cookies, MyskuCallback<Document> callback) {
            this.method = method;
            this.url = url;
            this.mediaType = null;
            this.body = null;
            this.cookies = cookies;
            this.callback = callback;
        }
    }
}
