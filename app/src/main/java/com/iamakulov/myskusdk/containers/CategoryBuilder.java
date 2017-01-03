package com.iamakulov.myskusdk.containers;

public class CategoryBuilder {
    private Category category;

    public CategoryBuilder() {
        category = new Category();
    }

    public CategoryBuilder setId(Category.Id id) {
        category.id = id;
        return this;
    }

    public CategoryBuilder setName(String name) {
        category.name = name;
        return this;
    }

    public Category build() {
        return category;
    }
}
