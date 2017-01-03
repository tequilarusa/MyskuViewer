package com.iamakulov.myskusdk.containers;

public class TagBuilder {
    private Tag tag;

    public TagBuilder() {
        tag = new Tag();
    }

    public TagBuilder setId(Tag.Id id) {
        tag.id = id;
        return this;
    }

    public TagBuilder setName(String name) {
        tag.name = name;
        return this;
    }

    public Tag Builder() {
        return tag;
    }
}
