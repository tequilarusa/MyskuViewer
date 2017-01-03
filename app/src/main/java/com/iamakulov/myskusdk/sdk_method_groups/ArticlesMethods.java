package com.iamakulov.myskusdk.sdk_method_groups;

import com.iamakulov.myskusdk.DocumentRetriever;
import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.MyskuSdk;
import com.iamakulov.myskusdk.containers.*;
import com.iamakulov.myskusdk.helpers.ArticleContentHelpers;
import com.iamakulov.myskusdk.helpers.UrlHelpers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticlesMethods {
    private DocumentRetriever retriever;
    private Map<String, String> cookies;

    public ArticlesMethods(DocumentRetriever retriever, Map<String, String> cookies) {
        this.retriever = retriever;
        this.cookies = cookies;
    }

    public void getRatedArticles(MyskuCallback<List<ArticlePreview>> callback) {
        getRatedArticles(0, callback);
    }

    public void getRatedArticles(int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getRatedArticlesUrl(page), callback);
    }

    public void getRecentArticles(MyskuCallback<List<ArticlePreview>> callback) {
        getRecentArticles(0, callback);
    }

    public void getRecentArticles(int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getRecentArticlesUrl(page), callback);
    }

    public void getTopArticles(MyskuCallback<List<ArticlePreview>> callback) {
        getTopArticles(0, MyskuSdk.TopArticlesInterval.WEEK, callback);
    }

    public void getTopArticles(int page, MyskuCallback<List<ArticlePreview>> callback) {
        getTopArticles(page, MyskuSdk.TopArticlesInterval.WEEK, callback);
    }

    public void getTopArticles(MyskuSdk.TopArticlesInterval interval, MyskuCallback<List<ArticlePreview>> callback) {
        getTopArticles(0, interval, callback);
    }

    public void getTopArticles(int page, MyskuSdk.TopArticlesInterval interval, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getTopArticlesUrl(page, interval), callback);
    }

    public void getArticlesByCategory(Category.Id categoryId, MyskuCallback<List<ArticlePreview>> callback) {
        getArticlesByCategory(categoryId, 0, callback);
    }

    public void getArticlesByCategory(Category.Id categoryId, int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getArticlesByCategoryUrl(categoryId, page), callback);
    }

    public void getArticlesByTag(Tag.Id tagId, MyskuCallback<List<ArticlePreview>> callback) {
        getArticlesByTag(tagId, 0, callback);
    }

    public void getArticlesByTag(Tag.Id tagId, int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getArticlesByTagUrl(tagId, page), callback);
    }

    public void getArticlesCreatedByUser(User.Id userId, MyskuCallback<List<ArticlePreview>> callback) {
        getArticlesCreatedByUser(userId, 0, callback);
    }

    public void getArticlesCreatedByUser(User.Id userId, int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getArticlesCreatedByUserUrl(userId, page), callback);
    }

    public void getArticlesFavoritedByUser(User.Id userId, MyskuCallback<List<ArticlePreview>> callback) {
        getArticlesFavoritedByUser(userId, 0, callback);
    }

    public void getArticlesFavoritedByUser(User.Id userId, int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getArticlesFavoritedByUserUrl(userId, page), callback);
    }

    public void searchArticles(String searchQuery, MyskuCallback<List<ArticlePreview>> callback) {
        searchArticles(searchQuery, 0, callback);
    }

    public void searchArticles(String searchQuery, int page, MyskuCallback<List<ArticlePreview>> callback) {
        retrieveArticles(UrlHelpers.getSearchUrl(searchQuery, page), callback);
    }

    private void retrieveArticles(String url, final MyskuCallback<List<ArticlePreview>> callback) {
        retriever.get(url, cookies, new MyskuCallback<Document>() {
            @Override
            public void onSuccess(Document document) {
                List<ArticlePreview> result = new ArrayList<>();

                for (Element element : document.body().select(".topic")) {
                    String articleLink = element.select(".content > .a").attr("href");
                    ArticleContent content = ArticleContentHelpers.parseArticleContent(element, articleLink);

                    String commentCount = element.select(".comments-total").text()
                        .replace(element.select(".comments-total .comment-title").text(), "")
                        .trim();

                    ArticlePreview articlePreview = new ArticlePreviewBuilder()
                        .setContent(content)
                        .setCommentCount(commentCount)
                        .build();

                    result.add(articlePreview);
                }

                callback.onSuccess(result);
            }

            @Override
            public void onError(MyskuError error) {
                callback.onError(error);
            }
        });
    }
}
