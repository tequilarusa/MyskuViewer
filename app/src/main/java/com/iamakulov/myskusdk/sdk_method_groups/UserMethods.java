package com.iamakulov.myskusdk.sdk_method_groups;

import com.iamakulov.myskusdk.DocumentRetriever;
import com.iamakulov.myskusdk.MyskuCallback;
import com.iamakulov.myskusdk.MyskuError;
import com.iamakulov.myskusdk.MyskuSdk;
import com.iamakulov.myskusdk.containers.*;
import com.iamakulov.myskusdk.helpers.UrlHelpers;
import com.iamakulov.myskusdk.helpers.UserHelpers;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMethods {
    private DocumentRetriever retriever;
    private Map<String, String> cookies;

    public UserMethods(DocumentRetriever retriever, Map<String, String> cookies) {
        this.retriever = retriever;
        this.cookies = cookies;
    }

    void getUserDetails(User.Id userId, final MyskuCallback<UserDetails> callback) {
        retriever.get(UrlHelpers.getUserUrlFromId(userId), cookies, new MyskuCallback<Document>() {
            @Override
            public void onSuccess(Document document) {
                Element body = document.body();

                Element autodetectedCountryRow = getRowDataByTitle(body, "Автоопределение страны:");
                String autodetectedCountry = autodetectedCountryRow != null ?
                    autodetectedCountryRow.text() :
                    "";

                Element friendsRow = getRowDataByTitle(body, "Друзья:");
                List<User> friends = friendsRow != null ?
                    extractUsersFromLinksList(friendsRow) :
                    new ArrayList<User>();

                String karma = body.select(".vote_block .total").text();
                String karmaVoteCount = body.select(".vote_block .count").text();

                Element lastVisitRow = getRowDataByTitle(body, "Последний визит:");
                String lastVisit = lastVisitRow != null ? lastVisitRow.text() : "";

                List<Category> participation = new ArrayList<>();
                for (Element e : getRowDataByTitle(body, "Состоит в:").select("a")) {
                    participation.add(
                        new CategoryBuilder()
                            .setId(UrlHelpers.getCategoryIdFromUrl(e.attr("href")))
                            .setName(e.text())
                            .build()
                    );
                }

                Element personalTable = null;
                for (Element element : body.select(".title")) {
                    if (element.text().equals("Личное")) {
                        personalTable = element.nextElementSibling();
                        break;
                    }
                }

                Map<String, String> personalData = new HashMap<>();
                if (personalTable != null) {
                    Elements personalTableRows = personalTable.select("tr");
                    for (Element element : personalTableRows) {
                        personalData.put(
                            element.select("td:first-child").text().trim().replaceAll(":$", ""),
                            element.select("td:last-child").text().trim()
                        );
                    }
                }

                String realName = body.select(".nickname .note").text().replaceAll("^\\(", "").replaceAll("\\)$", "");
                String registered = getRowDataByTitle(body, "Зарегистрирован:").text();

                Element subscribersRow = getRowDataByTitle(body, "Читатели:");
                List<User> subscribers = subscribersRow != null ?
                    extractUsersFromLinksList(subscribersRow) :
                    new ArrayList<User>();

                Element subscriptionsRow = getRowDataByTitle(body, "Подписан на:");
                List<User> subscriptions = subscriptionsRow != null ?
                    extractUsersFromLinksList(subscriptionsRow) :
                    new ArrayList<User>();

                String username = body.select(".nickname").text()
                    .replace(realName, "");


                UserDetails userDetails = new UserDetailsBuilder()
                    .setAutodetectedCountry(autodetectedCountry)
                    .setFriends(friends)
                    .setKarma(karma)
                    .setKarmaVoteCount(karmaVoteCount)
                    .setLastVisit(lastVisit)
                    .setParticipation(participation)
                    .setPersonalData(personalData)
                    .setRealName(realName)
                    .setRegistered(registered)
                    .setSubscribers(subscribers)
                    .setSubscriptions(subscriptions)
                    .setUser(UserHelpers.createUserFromUsername(username))
                    .build();

                callback.onSuccess(userDetails);
            }

            @Override
            public void onError(MyskuError error) {
                callback.onError(error);
            }
        });
    }

    private List<User> extractUsersFromLinksList(Element container) {
        List<User> result = new ArrayList<>();

        for (Element element : container.select("a")) {
            result.add(UserHelpers.createUserFromUsername(element.text()));
        }

        return result;
    }

    private Element getRowDataByTitle(Element rowsContainer, String title) {
        Element result = null;

        for (Element row : rowsContainer.select("tr")) {
            if (row.select("td:first-child").text().equals(title)) {
                result = row.select("td:last-child").first();
                break;
            }
        }

        return result;
    }

    void getUserComments(User.Id userId, MyskuCallback<List<Comment>> callback) {
        getUserComments(userId, 0, callback);
    }

    void getUserComments(User.Id userId, int page, final MyskuCallback<List<Comment>> callback) {
        retriever.get(UrlHelpers.getUserCommentsUrlFromId(userId, page), cookies, new MyskuCallback<Document>() {
            @Override
            public void onSuccess(Document document) {
                List<Comment> comments = new ArrayList<>();

                for (Element element : document.body().select("#content .comment")) {
                    String username = element.select(".author").text();
                    String authorThumbnail = element.select(".avatar").attr("src");
                    String body = element.select(".text").text();
                    String date = element.select(".date").text();
                    Comment.Id id = new Comment.Id(
                        element.select(".imglink").attr("href").replaceAll("^.*#comment", "")
                    );
                    String rating = element.select(".voting .total").text();

                    comments.add(
                        new CommentBuilder()
                            .setAuthor(UserHelpers.createUserFromUsername(username))
                            .setAuthorThumbnail(authorThumbnail)
                            .setBody(body)
                            .setDate(date)
                            .setId(id)
                            .setRating(rating)
                            .setReplies(new ArrayList<Comment>())
                            .build()
                    );
                }

                callback.onSuccess(comments);
            }

            @Override
            public void onError(MyskuError error) {
                callback.onError(error);
            }
        });
    }

    void getUserRating(User.Id userId, MyskuCallback<String> callback) {
        getUserRating(userId, MyskuSdk.UserRatingInterval.THIS_MONTH, callback);
    }

    void getUserRating(User.Id userId, MyskuSdk.UserRatingInterval interval, MyskuCallback<String> callback) {
        // TODO: ?
        throw new Error("UserMethods#getUserRating isn’t implemented");
    }

    void increaseUserKarma(User.Id userId, MyskuCallback<UserDetails> callback) {

    }

    void decreaseUserKarma(User.Id userId, MyskuCallback<UserDetails> callback) {

    }

    void addFriend(User.Id userId, MyskuCallback<UserDetails> callback) {

    }
}
