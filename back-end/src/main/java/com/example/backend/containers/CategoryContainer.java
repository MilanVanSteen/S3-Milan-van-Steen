package com.example.backend.containers;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.interfaces.CategoryInterface;
import com.example.backend.mappers.CategoryMapper;
import com.example.backend.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryContainer {
    private final CategoryInterface repo;

    public CategoryContainer(CategoryInterface repo) {
        this.repo = repo;
    }

    public List<Category> getAllCategories() {
        return CategoryMapper.toModelList((List<CategoryDTO>)repo.findAll());
    }

    public Category getCategoryById(int categoryID) {
        return CategoryMapper.toModel(repo.findById(categoryID).orElse(null));
    }


    public Category createCategory(Category category){
        return CategoryMapper.toModel(repo.save(CategoryMapper.toDTO(category)));
    }

    public boolean deleteCategory(int categoryID){
        try {
            repo.deleteById(categoryID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
