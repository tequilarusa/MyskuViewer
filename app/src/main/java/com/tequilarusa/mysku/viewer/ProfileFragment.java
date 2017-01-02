package com.tequilarusa.mysku.viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tequilarusa.mysku.R;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Maks on 29.12.2016.
 */

public class ProfileFragment extends Fragment {

    private EditText mLoginText;
    private EditText mPasswordText;
    private TextView mResultText;
    private Button mLoginButton;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        mLoginText = (EditText) view.findViewById(R.id.login_text);
        mPasswordText = (EditText) view.findViewById(R.id.password_text);
        mResultText = (TextView) view.findViewById(R.id.result_text);
        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask(mResultText).execute(mLoginText.getText().toString(), mPasswordText.getText().toString());
            }
        });

        return view;
    }



}
