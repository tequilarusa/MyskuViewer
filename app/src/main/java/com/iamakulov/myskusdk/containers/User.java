package com.iamakulov.myskusdk.containers;

public class User {
    Id id;
    String username;

    public Id getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public static class Id {
        public Id(String id) {
            this.id = id;
        }

        public String id;
    }
}
