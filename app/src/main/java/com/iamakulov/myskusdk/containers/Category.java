package com.iamakulov.myskusdk.containers;

public class Category {
    Id id;
    String name;

    public static class Id {
        public Id(String id) {
            this.id = id;
        }

        public String id;
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
