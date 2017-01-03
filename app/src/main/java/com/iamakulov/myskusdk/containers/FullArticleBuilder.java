package com.iamakulov.myskusdk.containers;

import java.util.List;

public class FullArticleBuilder {
    private FullArticle fullArticle;

    public FullArticleBuilder() {
        fullArticle = new FullArticle();
    }

    public FullArticleBuilder setContent(ArticleContent content) {
        fullArticle.content = content;
        return this;
    }

    public FullArticleBuilder setComments(List<Comment> comments) {
        fullArticle.comments = comments;
        return this;
    }

    public FullArticle build() {
        return fullArticle;
    }
}
