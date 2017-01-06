package com.tequilarusa.mysku.viewer.blog;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.containers.ArticlePreview;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.MainActivity;

import java.util.List;

/**
 * Created by Maks on 29.12.2016.
 */

public class BlogFragment extends ListFragment implements AdapterView.OnItemClickListener {


    private Parcelable mListState;
    private static final String LIST_STATE = "listState";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(mMessageReceiver,
//                new IntentFilter("receive-articles"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blog_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getMyskuSdk().articles().getRecentArticles(new MyskuCallback<List<ArticlePreview>>() {
            @Override
            public void onSuccess(final List<ArticlePreview> result) {
                BlogFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BlogFragment.this.setupAdapter(result);
                    }
                });
            }

            @Override
            public void onError(MyskuError error) {

            }
        });

    }

    public void setupAdapter(List<ArticlePreview> articles) {
        ArticleAdapter adapter = new ArticleAdapter((MainActivity) getActivity(), articles);
        setListAdapter(adapter);
        if (getView() != null) {
            getListView().setOnItemClickListener(BlogFragment.this);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onDestroy() {
//        // Unregister since the activity is about to be closed.
//        LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(mMessageReceiver);
//        super.onDestroy();
//    }
//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            ArrayList<ArticlePreview> articles = (ArrayList) intent.getSerializableExtra("articles");
//            ArticleAdapter adapter = new ArticleAdapter(getActivity(), articles);
//            setListAdapter(adapter);
//            if (getView() != null) {
//                getListView().setOnItemClickListener(BlogFragment.this);
//            }
//        }
//    };

}
