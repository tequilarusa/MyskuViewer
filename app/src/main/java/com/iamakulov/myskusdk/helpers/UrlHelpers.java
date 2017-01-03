package com.iamakulov.myskusdk.helpers;

import com.iamakulov.myskusdk.MyskuSdk;
import com.iamakulov.myskusdk.containers.ArticleContent;
import com.iamakulov.myskusdk.containers.Category;
import com.iamakulov.myskusdk.containers.Tag;
import com.iamakulov.myskusdk.containers.User;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class UrlHelpers {
    private static final String BASE_ADDRESS = "http://mysku.ru";

    public static String getArticleUrlFromId(ArticleContent.Id articleId) {
        return BASE_ADDRESS + "/blog/" + articleId.id + ".html";
    }

    public static Category.Id getCategoryIdFromUrl(String url) {
        try {
            String normalizedPath = new URL(url).getPath()
                .replaceAll("^/", "")
                .replaceAll("/$", "");

            return new Category.Id(normalizedPath);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String getCategoryUrlFromId(Category.Id categoryId) {
        return BASE_ADDRESS + "/" + categoryId.id;
    }

    public static Tag.Id getTagIdFromUrl(String url) {
        String[] splitUrl = url.split("/");
        return new Tag.Id(splitUrl[splitUrl.length - 1]);
    }

    public static String getTagUrlFromId(Tag.Id tagId) {
        return BASE_ADDRESS + "/tag/" + tagId.id;
    }

    public static String getRatedArticlesUrl(int page) {
        return BASE_ADDRESS + "/index/page" + (page + 1) + "/";
    }

    public static String getRecentArticlesUrl(int page) {
        return BASE_ADDRESS + "/blog/all/page" + (page + 1) + "/";
    }

    public static String getTopArticlesUrl(int page, MyskuSdk.TopArticlesInterval interval) {
        String intervalParam = null;
        switch (interval) {
            case DAY:
                intervalParam = "24h";
                break;
            case WEEK:
                intervalParam = "7d";
                break;
            case MONTH:
                intervalParam = "31d";
                break;
            case ALL_TIME:
                intervalParam = "all";
                break;
        }

        return BASE_ADDRESS + "/top/topic/" + intervalParam + "/page" + (page + 1) + "/";
    }

    public static String getArticlesByCategoryUrl(Category.Id categoryId, int page) {
        return getCategoryUrlFromId(categoryId) + "/page" + (page + 1) + "/";
    }

    public static String getArticlesByTagUrl(Tag.Id tagId, int page) {
        return getTagUrlFromId(tagId) + "/" + (page + 1) + "/";
    }

    public static String getArticlesCreatedByUserUrl(User.Id userId, int page) {
        return BASE_ADDRESS + "/my/" + userId.id + "/page" + (page + 1) + "/";
    }

    public static String getArticlesFavoritedByUserUrl(User.Id userId, int page) {
        return BASE_ADDRESS + "/profile/" + userId.id + "/favourites/page" + (page + 1) + "/";
    }

    public static String getSearchUrl(String searchQuery, int page) {
        // http://mysku.ru/search/topics/?q=%D0%BD%D0%BE%D0%B6
        try {
            return BASE_ADDRESS + "/search/topics/page" + (page + 1) + "/?q=" + URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getUserUrlFromId(User.Id userId) {
        return BASE_ADDRESS + "/profile/" + userId.id + "/";
    }

    public static String getUserCommentsUrlFromId(User.Id userId, int page) {
        return BASE_ADDRESS + "/my/" + userId.id + "/comment/page" + (page + 1) + "/";
    }
}
