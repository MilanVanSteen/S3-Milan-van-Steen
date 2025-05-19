package com.example.backend.Models;

import lombok.Getter;

@Getter
public class Category {
    private int CategoryID;
    private String Name;

    public Category(int categoryID, String name) {
        this.CategoryID = categoryID;
        this.Name = name;
    }
}
