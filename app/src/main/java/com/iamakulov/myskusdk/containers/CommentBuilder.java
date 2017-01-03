package com.iamakulov.myskusdk.containers;

import java.util.List;

public class CommentBuilder {
    private Comment comment;

    public CommentBuilder() {
        comment = new Comment();
    }

    public CommentBuilder setId(Comment.Id id) {
        comment.id = id;
        return this;
    }

    public CommentBuilder setBody(String body) {
        comment.body = body;
        return this;
    }

    public CommentBuilder setDate(String date) {
        comment.date = date;
        return this;
    }

    public CommentBuilder setAuthor(User author) {
        comment.author = author;
        return this;
    }

    public CommentBuilder setAuthorThumbnail(String authorThumbnail) {
        comment.authorThumbnail = authorThumbnail;
        return this;
    }

    public CommentBuilder setRating(String rating) {
        comment.rating = rating;
        return this;
    }

    public CommentBuilder setReplies(List<Comment> replies) {
        comment.replies = replies;
        return this;
    }

    public Comment build() {
        return comment;
    }
}
