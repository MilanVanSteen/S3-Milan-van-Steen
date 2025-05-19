package com.example.backend.Containers;

import com.example.backend.DTOs.CategoryDTO;
import com.example.backend.Interfaces.CategoryInterface;
import com.example.backend.Mappers.CategoryMapper;
import com.example.backend.Models.Category;

import java.util.List;

public class CategoryContainer {
    private final CategoryInterface repo;

    public CategoryContainer(CategoryInterface _repo) {
        this.repo = _repo;
    }

    public List<Category> GetAllCategories() {
        return CategoryMapper.toModelList((List<CategoryDTO>)repo.findAll());
    }

    public Category GetCategoryById(int categoryID) {
        return CategoryMapper.toModel(repo.findById(categoryID).orElse(null));
    }


    public Category CreateCategory(Category category){
        return CategoryMapper.toModel(repo.save(CategoryMapper.toDTO(category)));
    }

    public boolean DeleteCategory(int categoryID){
        try {
            repo.deleteById(categoryID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
