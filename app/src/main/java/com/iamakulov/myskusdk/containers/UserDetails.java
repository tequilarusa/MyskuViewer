package com.iamakulov.myskusdk.containers;

import java.util.List;
import java.util.Map;

public class UserDetails {
    User user;
    String realName;
    Map<String, String> personalData;
    List<User> friends;
    List<User> subscriptions;
    String autodetectedCountry;
    String registered;
    String lastVisit;
    String karma;
    String karmaVoteCount;
    List<User> subscribers;
    List<Category> participation;

    public User.Id getId() {
        return user.getId();
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getRealName() {
        return realName;
    }

    public Map<String, String> getPersonalData() {
        return personalData;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public List<User> getSubscriptions() {
        return subscriptions;
    }

    public String getAutodetectedCountry() {
        return autodetectedCountry;
    }

    public String getRegistered() {
        return registered;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public String getKarma() {
        return karma;
    }

    public String getKarmaVoteCount() {
        return karmaVoteCount;
    }

    public List<Category> getParticipation() {
        return participation;
    }
}
