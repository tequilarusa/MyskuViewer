package com.tequilarusa.mysku.viewer.blog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamakulov.myskusdk.containers.ArticlePreview;
import com.tequilarusa.mysku.R;
import com.tequilarusa.mysku.viewer.images.ImageLoader;

import java.util.ArrayList;

import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by MaximKashapov on 03.01.2017.
 */

public class ArticleAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ArticlePreview> objects;
    ImageLoader imageLoader;

    public ArticleAdapter(Context context, ArrayList<ArticlePreview> products) {
        ctx = context;
        objects = products;
        imageLoader = new ImageLoader(context);
        lInflater = (LayoutInflater) ctx
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.blog_item, parent, false);
        }
        final ArticlePreview article = getItem(position);

        ((TextView) view.findViewById(R.id.title_of_review)).setText(article.getContent().getTitle());

        TextView priceView = (TextView) view.findViewById(R.id.price);
        if (article.getContent().getPrice().isEmpty()) {
            priceView.setVisibility(View.GONE);
        } else {
            priceView.setText(article.getContent().getPrice());
        }


        // TODO: flags FROM_HTML_SEPARATOR_LINE_BREAK_DIV + FROM_HTML_SEPARATOR_LINE_BREAK_LIST + FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
        TextView marketLink = ((TextView) view.findViewById(R.id.link_to_shop));
        marketLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getContent().getMarketLink()));
                ctx.startActivity(browserIntent);
            }
        });

        FlowTextView sortDesc = ((FlowTextView) view.findViewById(R.id.short_text_description));
        ImageView produceImage = ((ImageView) view.findViewById(R.id.title_image));
        produceImage.setTag(article.getContent().getProductImage());
        imageLoader.DisplayImage(article.getContent().getProductImage(), produceImage);
        sortDesc.setText(Html.fromHtml(article.getContent().getShortText()));
        sortDesc.setTextSize(50);
//        Display display = ((Activity)ctx).getWindowManager().getDefaultDisplay();
//        FlowTextHelper.tryFlowText(article.getContent().getShortText(), produceImage, sortDesc, display);


        ((TextView) view.findViewById(R.id.quantity_of_favourite)).setText(article.getContent().getRating());

        ((TextView) view.findViewById(R.id.date_of_publication)).setText(article.getContent().getDate());
        ((TextView) view.findViewById(R.id.quantity_of_looks)).setText(article.getContent().getViewCount());

        ((TextView) view.findViewById(R.id.author_of_review)).setText(article.getContent().getAuthor().getUsername());
        ((TextView) view.findViewById(R.id.number_of_comments)).setText(article.getCommentCount());

        return view;
    }
}
