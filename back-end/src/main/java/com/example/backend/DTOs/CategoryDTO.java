package com.example.backend.DTOs;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class CategoryDTO {
    @Id
    private int CategoryID;
    private String Name;

    public CategoryDTO(int categoryID, String name) {
        this.CategoryID = categoryID;
        this.Name = name;
    }
}
