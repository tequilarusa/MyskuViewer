package com.iamakulov.myskusdk.containers;

import java.util.List;
import java.util.Map;

public class UserDetailsBuilder {
    private UserDetails userDetails;
    private List<User> subscripers;

    public UserDetailsBuilder() {
        userDetails = new UserDetails();
    }

    public UserDetailsBuilder setUser(User user) {
        userDetails.user = user;
        return this;
    }

    public UserDetailsBuilder setRealName(String realName) {
        userDetails.realName = realName;
        return this;
    }

    public UserDetailsBuilder setPersonalData(Map<String, String> personalData) {
        userDetails.personalData = personalData;
        return this;
    }

    public UserDetailsBuilder setFriends(List<User> friends) {
        userDetails.friends = friends;
        return this;
    }

    public UserDetailsBuilder setSubscribers(List<User> subscripers) {
        userDetails.subscribers = subscripers;
        return this;
    }

    public UserDetailsBuilder setSubscriptions(List<User> subscriptions) {
        userDetails.subscriptions = subscriptions;
        return this;
    }

    public UserDetailsBuilder setAutodetectedCountry(String autodetectedCountry) {
        userDetails.autodetectedCountry = autodetectedCountry;
        return this;
    }

    public UserDetailsBuilder setRegistered(String registered) {
        userDetails.registered = registered;
        return this;
    }

    public UserDetailsBuilder setLastVisit(String lastVisit) {
        userDetails.lastVisit = lastVisit;
        return this;
    }

    public UserDetailsBuilder setKarma(String karma) {
        userDetails.karma = karma;
        return this;
    }

    public UserDetailsBuilder setKarmaVoteCount(String karmaVoteCount) {
        userDetails.karmaVoteCount = karmaVoteCount;
        return this;
    }

    public UserDetailsBuilder setParticipation(List<Category> participation) {
        userDetails.participation = participation;
        return this;
    }

    public UserDetails build() {
        return userDetails;
    }
}
