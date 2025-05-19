package com.example.backend.Mappers;

import com.example.backend.DTOs.CategoryDTO;
import com.example.backend.Models.Category;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CategoryDTO> toDTOList(List<Category> categories) {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public static List<Category> toModelList(List<CategoryDTO> categoryDTOs) {
        if (categoryDTOs == null) {
            return new ArrayList<>();
        }
        return categoryDTOs.stream()
                .map(CategoryMapper::toModel)
                .toList();
    }
}
