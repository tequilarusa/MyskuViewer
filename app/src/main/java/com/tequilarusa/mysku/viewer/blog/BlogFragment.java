package com.tequilarusa.mysku.viewer.blog;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.MyskuSdk;
import com.iamakulov.myskusdk.MyskuSdkFactory;
import com.iamakulov.myskusdk.containers.ArticlePreview;
import com.tequilarusa.mysku.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maks on 29.12.2016.
 */

public class BlogFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private MyskuSdk mMyskuSdk;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<ArticlePreview> articles = (ArrayList) intent.getSerializableExtra("articles");
            ArticleAdapter adapter = new ArticleAdapter(getActivity(), articles);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(BlogFragment.this);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyskuSdk = MyskuSdkFactory.createSdk();
        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("receive-articles"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blog_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMyskuSdk.articles().getRecentArticles(new MyskuCallback<List<ArticlePreview>>() {
            @Override
            public void onSuccess(List<ArticlePreview> result) {
                Intent intent = new Intent("receive-articles");
                intent.putExtra("articles", new ArrayList<>(result));
                LocalBroadcastManager.getInstance(BlogFragment.this.getActivity()).sendBroadcast(intent);
            }

            @Override
            public void onError(MyskuError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
