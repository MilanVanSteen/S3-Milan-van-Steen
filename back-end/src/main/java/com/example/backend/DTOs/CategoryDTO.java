package com.example.backend.DTOs;

import lombok.Getter;

@Getter
public class CategoryDTO {
    private int CategoryID;
    private String Name;

    public CategoryDTO(int categoryID, String name) {
        this.CategoryID = categoryID;
        this.Name = name;
    }
}
