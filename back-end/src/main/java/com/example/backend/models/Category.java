package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int categoryID;
    private String name;

    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(){}
}
