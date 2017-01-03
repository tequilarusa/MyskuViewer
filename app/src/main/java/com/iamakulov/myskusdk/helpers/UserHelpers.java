package com.iamakulov.myskusdk.helpers;

import com.iamakulov.myskusdk.containers.User;
import com.iamakulov.myskusdk.containers.UserBuilder;

public class UserHelpers {
    public static User createUserFromUsername(String username) {
        return new UserBuilder()
            .setId(new User.Id(username))
            .setUsername(username)
            .build();
    }
}
