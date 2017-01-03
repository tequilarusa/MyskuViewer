package com.iamakulov.myskusdk.containers;

public class ArticlePreviewBuilder {
    private ArticlePreview articlePreview;

    public ArticlePreviewBuilder() {
        articlePreview = new ArticlePreview();
    }

    public ArticlePreviewBuilder setContent(ArticleContent content) {
        articlePreview.content = content;
        return this;
    }

    public ArticlePreviewBuilder setCommentCount(String commentCount) {
        articlePreview.commentCount = commentCount;
        return this;
    }

    public ArticlePreview build() {
        return articlePreview;
    }
}
