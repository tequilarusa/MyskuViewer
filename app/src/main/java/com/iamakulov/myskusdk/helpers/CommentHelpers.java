package com.iamakulov.myskusdk.helpers;

import com.iamakulov.myskusdk.containers.Comment;
import com.iamakulov.myskusdk.containers.CommentBuilder;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CommentHelpers {
    public static List<Comment> parseCommentList(Element commentsContainer) {
        if (commentsContainer == null) {
            return new ArrayList<>();
        }

        List<Comment> comments = new ArrayList<>();
        for (Element e : commentsContainer.select("> .comment")) {
            Element commentContent = e.select("> .content").first();
            Elements childComments = e.select("> .comment-children");

            comments.add(
                new CommentBuilder()
                    .setId(new Comment.Id(e.id().replace("comment_id_", "")))
                    .setAuthor(UserHelpers.createUserFromUsername(commentContent.select(".user_name").text()))
                    .setAuthorThumbnail(commentContent.select(".avatar").attr("src"))
                    .setBody(commentContent.select(".text").html())
                    .setDate(commentContent.select(".date").text())
                    .setRating(commentContent.select(".voting .total").text())
                    .setReplies(childComments.isEmpty() ? null : parseCommentList(childComments.first()))
                    .build()
            );
        }

        return comments;
    }
}