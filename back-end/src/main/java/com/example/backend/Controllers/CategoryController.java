package com.example.backend.Controllers;

import com.example.backend.Containers.CategoryContainer;
import com.example.backend.Models.Category;
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
    public ResponseEntity<List<Category>> GetAllCategories() {
        List<Category> categories = categoryContainer.GetAllCategories();
        if (categories == null || !categories.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    @GetMapping("/getCategoryById")
    public ResponseEntity<Category> GetCategoryById(@RequestParam int categoryID) {
        Category category = categoryContainer.GetCategoryById(categoryID);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Category> CreateCategory(@RequestBody Category category) {
        Category createdCategory = categoryContainer.CreateCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> DeleteCategory(@RequestParam int categoryID) {
        if (categoryContainer.DeleteCategory(categoryID)){
            return ResponseEntity.ok("Deleted category");
        }
        return ResponseEntity.noContent().build();
    }
}
