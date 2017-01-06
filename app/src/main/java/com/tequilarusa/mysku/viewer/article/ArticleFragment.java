package com.tequilarusa.mysku.viewer.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.containers.ArticleContent;
import com.iamakulov.myskusdk.containers.FullArticle;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.MainActivity;
import com.tequilarusa.mysku.viewer.images.ImageLoader;

import uk.co.deanwild.flowtextview.FlowTextView;

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



        ((MainActivity)getActivity()).getMyskuSdk().article().getFullArticle(mArticleId, new MyskuCallback<FullArticle>() {
            @Override
            public void onSuccess(final FullArticle result) {
                ArticleFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader imageLoader = ((MainActivity) getActivity()).getImageLoader();
                        ((TextView)view.findViewById(R.id.review_title)).setText(result.getContent().getTitle());
                        FlowTextView fullText = (FlowTextView)view.findViewById(R.id.full_text);
                        fullText.setText(Html.fromHtml(result.getContent().getShortText(), new ImageLoader(getActivity(), fullText), null));
                        ImageView mainImage = (ImageView) view.findViewById(R.id.title_image);
                        mainImage.setTag(result.getContent().getProductImage());
                        imageLoader.DisplayImage(result.getContent().getProductImage(), mainImage);
                    }
                });

            }

            @Override
            public void onError(MyskuError error) {

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
