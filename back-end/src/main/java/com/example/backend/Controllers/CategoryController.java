package com.example.backend.Controllers;

import com.example.backend.Containers.CategoryContainer;
import com.example.backend.Interfaces.CategoryInterface;
import com.example.backend.Models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class CategoryController {
    private final CategoryContainer categoryContainer;

    public CategoryController(CategoryInterface categoryInterface) {
        this.categoryContainer = new CategoryContainer(categoryInterface);
    }

    public ResponseEntity<List<Category>> GetAllCategories() {
        List<Category> categories = categoryContainer.GetAllCategories();
        if (categories == null || !categories.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    public ResponseEntity<Category> GetCategoryById(@RequestParam int categoryID) {
        Category category = categoryContainer.GetCategoryById(categoryID);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Category> CreateCategory(@RequestBody Category category) {
        Category createdCategory = categoryContainer.CreateCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    public ResponseEntity<String> DeleteCategory(@RequestParam int categoryID) {
        if (categoryContainer.DeleteCategory(categoryID)){
            return ResponseEntity.ok("Deleted category");
        }
        return ResponseEntity.noContent().build();
    }
}
