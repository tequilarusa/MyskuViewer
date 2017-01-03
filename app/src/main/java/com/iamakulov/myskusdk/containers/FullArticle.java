package com.iamakulov.myskusdk.containers;

import java.util.List;

public class FullArticle {
    ArticleContent content;
    List<Comment> comments;

    public ArticleContent getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
