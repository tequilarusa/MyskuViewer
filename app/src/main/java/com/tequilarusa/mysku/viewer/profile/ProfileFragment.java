package com.tequilarusa.mysku.viewer.profile;

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
import com.tequilarusa.mysku.viewer.profile.LoginTask;

/**
 * Created by Maks on 29.12.2016.
 */

public class ProfileFragment extends Fragment {

    private EditText mLoginText;
    private EditText mPasswordText;
    private TextView mResultText;
    private Button mLoginButton;

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
