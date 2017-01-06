package com.tequilarusa.mysku.viewer.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamakulov.myskusdk.containers.ArticleContent;
import com.iamakulov.myskusdk.containers.ArticlePreview;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.MainActivity;
import com.tequilarusa.mysku.viewer.article.ArticleFragment;
import com.tequilarusa.mysku.viewer.images.ImageLoader;

import java.util.List;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by MaximKashapov on 03.01.2017.
 */

public class ArticleAdapter extends BaseAdapter {
    private final ImageLoader imageLoader;
    private MainActivity mainActivity;
    private LayoutInflater lInflater;
    private List<ArticlePreview> objects;



    public ArticleAdapter(MainActivity context, List<ArticlePreview> products) {
        mainActivity = context;
        objects = products;
        imageLoader = context.getImageLoader();
        lInflater = (LayoutInflater) mainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ArticlePreview getItem(int i) {
        return (ArticlePreview) objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = lInflater.inflate(R.layout.blog_item, parent, false);
            holder.titleOfReview = (TextView) view.findViewById(R.id.title_of_review);
            holder.linkToShop = (TextView) view.findViewById(R.id.link_to_shop);
            holder.price = (TextView) view.findViewById(R.id.price);
            holder.shortTextDescription = (FlowTextView) view.findViewById(R.id.short_text_description);
            holder.titleImage = (ImageView) view.findViewById(R.id.title_image);
            holder.quantityOfFavourite = (TextView) view.findViewById(R.id.quantity_of_favourite);
            holder.dateOfPublication = (TextView) view.findViewById(R.id.date_of_publication);
            holder.quantityOfLooks = (TextView) view.findViewById(R.id.quantity_of_looks);
            holder.authorOfReview = (TextView) view.findViewById(R.id.author_of_review);
            holder.numberOfComments = (TextView) view.findViewById(R.id.number_of_comments);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ArticlePreview article = getItem(position);
        holder.articleId = article.getContent().getId();
        holder.titleOfReview.setText(article.getContent().getTitle());

        if (article.getContent().getPrice().isEmpty()) {
            holder.price.setVisibility(View.GONE);
        } else {
            holder.price.setText(article.getContent().getPrice());
        }

        holder.linkToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getContent().getMarketLink()));
                mainActivity.startActivity(browserIntent);
            }
        });

        holder.titleImage.setTag(article.getContent().getProductImage());
        imageLoader.DisplayImage(article.getContent().getProductImage(), holder.titleImage);
        holder.shortTextDescription.setText(Html.fromHtml(article.getContent().getShortText()));
        holder.shortTextDescription.setTextSize(50);
        holder.quantityOfFavourite.setText(article.getContent().getRating());
        holder.dateOfPublication.setText(article.getContent().getDate());
        holder.quantityOfLooks.setText(article.getContent().getViewCount());
        holder.authorOfReview.setText(article.getContent().getAuthor().getUsername());
        holder.numberOfComments.setText(article.getCommentCount());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArticleFragment article = new ArticleFragment();
                article.setArticleId(((ViewHolder)view.getTag()).articleId);
                mainActivity.replaceFragment(article);
            }
        });

        return view;
    }

    static class ViewHolder {
        ArticleContent.Id articleId;
        TextView titleOfReview;
        TextView price;
        TextView linkToShop;
        FlowTextView shortTextDescription;
        ImageView titleImage;
        TextView quantityOfFavourite;
        TextView dateOfPublication;
        TextView quantityOfLooks;
        TextView authorOfReview;
        TextView numberOfComments;

    }
}
