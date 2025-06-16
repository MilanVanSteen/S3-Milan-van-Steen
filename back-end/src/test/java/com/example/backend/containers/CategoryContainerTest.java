package com.example.backend.containers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.interfaces.CategoryInterface;
import com.example.backend.mappers.CategoryMapper;
import com.example.backend.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryContainerTest {

    @Mock
    private CategoryInterface categoryRepo;

    private CategoryContainer categoryContainer;

    @BeforeEach
    void setUp() {
        categoryContainer = new CategoryContainer(categoryRepo);
    }

    @Test
    void testGetAllCategoriesReturnsCategories() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO(1, "Category A");
        when(categoryRepo.findAll()).thenReturn(List.of(categoryDTO));

        // Act
        List<Category> categories = categoryContainer.getAllCategories();

        // Assert
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals("Category A", categories.get(0).getName());
    }

    @Test
    void testGetCategoryByIdReturnsCategory() {
        // Arrange
        int id = 1;
        CategoryDTO categoryDTO = new CategoryDTO(id, "Category A");
        when(categoryRepo.findById(id)).thenReturn(Optional.of(categoryDTO));

        // Act
        Category category = categoryContainer.getCategoryById(id);

        // Assert
        assertNotNull(category);
        assertEquals("Category A", category.getName());
    }

    @Test
    void testGetCategoryByIdReturnsNullWhenNotFound() {
        // Arrange
        when(categoryRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        Category category = categoryContainer.getCategoryById(999);

        // Assert
        assertNull(category);
    }

    @Test
    void testCreateCategoryReturnsCreatedCategory() {
        // Arrange
        Category category = new Category(1, "New Category");
        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);
        when(categoryRepo.save(any(CategoryDTO.class))).thenReturn(categoryDTO);

        // Act
        Category created = categoryContainer.createCategory(category);

        // Assert
        assertNotNull(created);
        assertEquals(category.getName(), created.getName());
        verify(categoryRepo, times(1)).save(any(CategoryDTO.class));
    }

    @Test
    void testDeleteCategoryReturnsTrueOnSuccess() {
        // Arrange
        doNothing().when(categoryRepo).deleteById(1);

        // Act
        boolean result = categoryContainer.deleteCategory(1);

        // Assert
        assertTrue(result);
        verify(categoryRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCategoryReturnsFalseOnException() {
        // Arrange
        doThrow(new RuntimeException()).when(categoryRepo).deleteById(1);

        // Act
        boolean result = categoryContainer.deleteCategory(1);

        // Assert
        assertFalse(result);
        verify(categoryRepo, times(1)).deleteById(1);
    }
}