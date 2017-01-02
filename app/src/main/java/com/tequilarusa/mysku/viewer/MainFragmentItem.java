package com.tequilarusa.mysku.viewer;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Maks on 29.12.2016.
 */

public class MainFragmentItem extends ListFragment {
    private String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        this.url = "http://mysku.ru/rss/";
//        rssService = new RssService(this);
//        AsyncTask<String, Void, List<Article>> task = rssService.execute(url);
        return v;
    }

}
