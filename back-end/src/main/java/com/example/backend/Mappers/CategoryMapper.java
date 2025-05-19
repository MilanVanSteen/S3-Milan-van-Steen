package com.example.backend.Mappers;

import com.example.backend.DTOs.CategoryDTO;
import com.example.backend.Models.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        if(category == null) {
            return null;
        }
        return new CategoryDTO(category.getCategoryID(), category.getName());
    }

    public static Category toModel(CategoryDTO categoryDTO) {
        if(categoryDTO == null) {
            return null;
        }
        return new Category(categoryDTO.getCategoryID(), categoryDTO.getName());
    }
}
