package com.iamakulov.myskusdk.helpers;

import com.iamakulov.myskusdk.containers.*;
import com.iamakulov.myskusdk.containers.ArticleContentBuilder.Kind;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ArticleContentHelpers {
    public static ArticleContent parseArticleContent(Element element, String articleUrl) {
        boolean isCouponArticle = ArticleContentHelpers.isCouponArticle(element);

        List<Category> categories = new ArrayList<>();
        for (Element e : element.select(".category-list a")) {
            Category.Id id = UrlHelpers.getCategoryIdFromUrl(e.attr("href"));
            String name = e.text().trim();

            categories.add(
                new CategoryBuilder()
                    .setId(id)
                    .setName(name)
                    .build()
            );
        }

        List<Tag> tags = new ArrayList<>();
        for (Element e : element.select(".tags a")) {
            Tag.Id id = UrlHelpers.getTagIdFromUrl(e.attr("href"));
            String name = e.text().trim();

            tags.add(
                new TagBuilder()
                    .setId(id)
                    .setName(name)
                    .Builder()
            );
        }

        ArticleContentBuilder factory = new ArticleContentBuilder(isCouponArticle ? Kind.COUPON : Kind.NORMAL)
            .setAuthor(UserHelpers.createUserFromUsername(element.select(".author a").text().trim()))
            .setBody(ArticleContentHelpers.cleanUpBody(element.select(".content").html().trim()))
            .setShortText(element.select(".sku_short_text.description").html().trim())
            .setCategories(categories)
            .setProductImage(element.select(".content .product_image").attr("src"))
            .setDate(element.select(".date-info").attr("data-date-info"))
            .setId(new ArticleContent.Id(articleUrl))
            .setPrice(element.select(".sku-info .price").text().replaceAll("[^\\d.$]", "").trim())
            .setRating(
                // `.total` contains the total rating as a text node next to the another counter,
                // so we need to remove the another counter’s text
                element.select(".content .total").text().replace(
                    element.select(".content .total .counter").text(),
                    ""
                ).trim()
            )
            .setTags(tags)
            .setTitle(element.select(".topic-title").text().trim())
            .setViewCount(
                element.select(".read").text().replace(
                    element.select(".read .mobile-only").text(),
                    ""
                ).trim()
            );

        if (isCouponArticle) {
            factory
                .setMarketLink(element.select(".coupon-go-button a").attr("href"))
                .setCouponCode(element.select(".coupon-body").text().trim())
                .setCouponExpirationDate(element.select(".coupon-info-text span").text().trim());
        } else {
            factory
                .setMarketLink(element.select(".sku-info a").attr("href"))
                .setGoingToPurchase(element.select(".purchase_button .number").text());
        }

        return factory.build();
    }

    private static boolean isCouponArticle(Element element) {
        return element.select(".coupon-go-button").size() > 0;
    }

    public static String cleanUpBody(String rawBody) {
        // TODO: implement
        return rawBody;
    }
}
