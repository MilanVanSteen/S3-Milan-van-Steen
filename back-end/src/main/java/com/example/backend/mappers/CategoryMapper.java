package com.example.backend.mappers;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    // Private constructor to prevent instantiation
    private CategoryMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CategoryDTO toDTO(Category category) {
        if(category == null) {
            return null;
        }
        if(category.getCategoryID() == 0) {
            // New category, don't set ID
            return new CategoryDTO(category.getName());
        } else {
            // Existing category with ID
            return new CategoryDTO(category.getCategoryID(), category.getName());
        }
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
