package com.iamakulov.myskusdk.containers;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(User.Id id) {
        user.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.username = username;
        return this;
    }

    public User build() {
        return user;
    }
}
