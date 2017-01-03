package com.iamakulov.myskusdk.containers;

public class CouponArticleContent extends ArticleContent {
    String couponCode;
    String couponExpirationDate;

    public String getCouponCode() {
        return couponCode;
    }

    public String getCouponExpirationDate() {
        return couponExpirationDate;
    }
}
