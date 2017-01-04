package com.iamakulov.myskusdk.containers;

import java.util.List;

public abstract class ArticleContent {
    Id id;
    String title;
    String body;
    String shortText;
    List<Category> categories;
    String price;
    String marketLink;
    List<Tag> tags;
    String rating;
    String date;
    User author;
    String viewCount;
    String productImage;

    public Id getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getShortText() {
        return shortText;
    }

    public String getProductImage() {
        return productImage;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getPrice() {
        return price;
    }

    public String getMarketLink() {
        return marketLink;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    public User getAuthor() {
        return author;
    }

    public String getViewCount() {
        return viewCount;
    }

    public static class Id {
        public Id(String id) {
            this.id = id;
        }

        public String id;
    }
}
