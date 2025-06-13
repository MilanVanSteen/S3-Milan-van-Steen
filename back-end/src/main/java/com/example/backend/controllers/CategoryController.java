package com.example.backend.controllers;

import com.example.backend.containers.CategoryContainer;
import com.example.backend.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryContainer categoryContainer;

    @Autowired
    public CategoryController(CategoryContainer categoryContainer) {
        this.categoryContainer = categoryContainer;
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryContainer.getAllCategories();
        if (categories == null || !categories.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<Category> getCategoryById(@RequestParam int categoryID) {
        Category category = categoryContainer.getCategoryById(categoryID);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryContainer.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestParam int categoryID) {
        if (categoryContainer.deleteCategory(categoryID)){
            return ResponseEntity.ok("Deleted category");
        }
        return ResponseEntity.noContent().build();
    }
}
