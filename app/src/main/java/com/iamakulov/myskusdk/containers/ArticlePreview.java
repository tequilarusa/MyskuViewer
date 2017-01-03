package com.iamakulov.myskusdk.containers;

public class ArticlePreview {
    ArticleContent content;
    String commentCount;

    public ArticleContent getContent() {
        return content;
    }

    public ArticlePreview setContent(ArticleContent content) {
        this.content = content;
        return this;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public ArticlePreview setCommentCount(String commentCount) {
        this.commentCount = commentCount;
        return this;
    }
}
