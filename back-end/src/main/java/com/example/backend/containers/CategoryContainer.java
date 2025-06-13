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
