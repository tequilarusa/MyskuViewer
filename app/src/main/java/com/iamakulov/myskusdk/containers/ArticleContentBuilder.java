package com.iamakulov.myskusdk.containers;

import java.util.List;

public class ArticleContentBuilder {
    ArticleContent content;

    public ArticleContentBuilder(Kind kind) {
        content = kind == Kind.NORMAL ? new NormalArticleContent() : new CouponArticleContent();
    }

    public ArticleContentBuilder setId(ArticleContent.Id id) {
        content.id = id;
        return this;
    }

    public ArticleContentBuilder setTitle(String title) {
        content.title = title;
        return this;
    }

    public ArticleContentBuilder setBody(String body) {
        content.body = body;
        return this;
    }

    public ArticleContentBuilder setShortText(String shortText) {
        content.shortText = shortText;
        return this;
    }

    public ArticleContentBuilder setProductImage(String src) {
        content.productImage = src;
        return this;
    }

    public ArticleContentBuilder setCategories(List<Category> categories) {
        content.categories = categories;
        return this;
    }

    public ArticleContentBuilder setPrice(String price) {
        content.price = price;
        return this;
    }

    public ArticleContentBuilder setMarketLink(String marketLink) {
        content.marketLink = marketLink;
        return this;
    }

    public ArticleContentBuilder setTags(List<Tag> tags) {
        content.tags = tags;
        return this;
    }

    public ArticleContentBuilder setRating(String rating) {
        content.rating = rating;
        return this;
    }

    public ArticleContentBuilder setDate(String date) {
        content.date = date;
        return this;
    }

    public ArticleContentBuilder setAuthor(User author) {
        content.author = author;
        return this;
    }

    public ArticleContentBuilder setViewCount(String viewCount) {
        content.viewCount = viewCount;
        return this;
    }

    public ArticleContentBuilder setGoingToPurchase(String goingToPurchase) {
        NormalArticleContent couponContent = (NormalArticleContent) content;
        couponContent.goingToPurchase = goingToPurchase;
        return this;
    }

    public ArticleContentBuilder setCouponCode(String couponCode) {
        CouponArticleContent couponContent = (CouponArticleContent) content;
        couponContent.couponCode = couponCode;
        return this;
    }

    public ArticleContentBuilder setCouponExpirationDate(String couponExpirationDate) {
        CouponArticleContent couponContent = (CouponArticleContent) content;
        couponContent.couponExpirationDate = couponExpirationDate;
        return this;
    }

    public ArticleContent build() {
        return content;
    }

    public enum Kind {
        NORMAL,
        COUPON
    }
}
