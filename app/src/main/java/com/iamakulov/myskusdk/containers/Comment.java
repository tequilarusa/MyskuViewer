package com.iamakulov.myskusdk.containers;

import java.util.List;

public class Comment {
    Id id;
    String body;
    String date;
    User author;
    String authorThumbnail;
    String rating;
    List<Comment> replies;

    public static class Id {
        public Id(String id) {
            this.id = id;
        }

        public String id;
    }

    public Id getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public User getAuthor() {
        return author;
    }

    public String getAuthorThumbnail() {
        return authorThumbnail;
    }

    public String getRating() {
        return rating;
    }

    public List<Comment> getReplies() {
        return replies;
    }
}
