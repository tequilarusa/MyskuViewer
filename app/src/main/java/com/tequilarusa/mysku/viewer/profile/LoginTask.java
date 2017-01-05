package com.tequilarusa.mysku.viewer.profile;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;

import okhttp3.JavaNetCookieJar;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Maks on 03.01.2017.
 */

public class LoginTask extends AsyncTask<String, Void, String> {

    private TextView mTextView;

    public LoginTask(TextView textView) {
        mTextView = textView;
    }


    @Override
    protected String doInBackground(String... params) {

        String result = null;

        String url = "http://mysku.ru/login/";

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("submit_open_login", "go")
                .addFormDataPart("open_login", "")
                .addFormDataPart("return", "http://mysku.ru/")
                .addFormDataPart("login", params[0]) //"crazyrock"
                .addFormDataPart("password", params[1]) //"Rtyuehejj"
                .addFormDataPart("remember", "on")
                .addFormDataPart("submit_login", "")
                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            client.newCall(request).execute();
            for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                if ("key".equals(cookie.getName())) {
                    result = cookie.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    protected void onPostExecute(String key) {
        if (key == null) {
            mTextView.setText("Login failed");
        } else {
            mTextView.setText("Retrieved key: " + key);
        }

    }

}
