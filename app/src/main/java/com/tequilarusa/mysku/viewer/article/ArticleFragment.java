package com.tequilarusa.mysku.viewer.article;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.containers.ArticleContent;
import com.iamakulov.myskusdk.containers.FullArticle;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.MainActivity;

/**
 * Created by MaximKashapov on 05.01.2017.
 */

public class ArticleFragment extends Fragment {

    private ArticleContent.Id mArticleId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.article_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.article_actions, menu);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ProgressDialog progressDialog = ((MainActivity) ArticleFragment.this.getActivity()).getProgressDialog();

        ((MainActivity) getActivity()).getMyskuSdk().article().getFullArticle(mArticleId, new MyskuCallback<FullArticle>() {

            @Override
            public void onSuccess(final FullArticle result) {
                ArticleFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = "<style>img{display: inline; height: auto; max-width: 100%; margin-bottom: 5px;}</style>" + result.getContent().getShortText();
                        ((TextView) view.findViewById(R.id.review_title)).setText(result.getContent().getTitle());
                        WebView articleView = (WebView) view.findViewById(R.id.full_text);
                        articleView.setWebViewClient(new WebViewClient() {
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                        articleView.loadData(data, "text/html;charset=UTF8", null);
                    }
                });

            }

            @Override
            public void onError(MyskuError error) {

            }
        });
        progressDialog.setMessage("Loading..Please wait.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final Animation show_fab = AnimationUtils.loadAnimation(this.getActivity().getApplication(), R.anim.fab_show);
        final Animation hide_fab = AnimationUtils.loadAnimation(this.getActivity().getApplication(), R.anim.fab_hide);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        final FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.fab_1);
        final FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab_2);
        final FloatingActionButton fab3 = (FloatingActionButton) view.findViewById(R.id.fab_3);

        fab.setOnClickListener(new View.OnClickListener() {

            private boolean opened = false;

            @Override
            public void onClick(View view) {
                opened = !opened;

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
                if (opened) {
                    layoutParams.bottomMargin += (int) (fab1.getHeight() * 1.5);
                    fab1.startAnimation(show_fab);
                } else {
                    layoutParams.bottomMargin -= (int) (fab1.getHeight() * 1.5);
                    fab1.startAnimation(hide_fab);
                }
                fab1.setLayoutParams(layoutParams);
                fab1.setClickable(opened);


                layoutParams = (FrameLayout.LayoutParams) fab2.getLayoutParams();
                if (opened) {
                    layoutParams.bottomMargin += (int) (fab2.getHeight() * 2.7);
                    fab2.startAnimation(show_fab);
                } else {
                    layoutParams.bottomMargin -= (int) (fab2.getHeight() * 2.7);
                    fab2.startAnimation(hide_fab);
                }
                fab2.setLayoutParams(layoutParams);
                fab2.setClickable(opened);

                layoutParams = (FrameLayout.LayoutParams) fab3.getLayoutParams();
                if (opened) {
                    layoutParams.bottomMargin += (int) (fab3.getHeight() * 3.9);
                    fab3.startAnimation(show_fab);
                } else {
                    layoutParams.bottomMargin -= (int) (fab3.getHeight() * 3.9);
                    fab3.startAnimation(hide_fab);
                }
                fab3.setLayoutParams(layoutParams);
                fab3.setClickable(opened);
            }
        });
    }

    public void setArticleId(ArticleContent.Id articleId) {
        mArticleId = articleId;
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Get item selected and deal with it
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                //called when the up affordance/carat in actionbar is pressed
//                getActivity().onBackPressed();
//                return true;
//        }
//        return false;
//    }
}
