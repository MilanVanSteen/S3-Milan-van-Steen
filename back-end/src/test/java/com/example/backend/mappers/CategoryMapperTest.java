package com.example.backend.mappers;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.models.Category;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    void testToDTO_withNewCategory() {
        // Arrange
        Category category = new Category(0, "NewCat");

        // Act
        CategoryDTO dto = CategoryMapper.toDTO(category);

        // Assert
        assertNotNull(dto);
        assertEquals(0, dto.getCategoryID());
        assertEquals("NewCat", dto.getName());
    }

    @Test
    void testToDTO_withExistingCategory() {
        // Arrange
        Category category = new Category(5, "ExistingCat");

        // Act
        CategoryDTO dto = CategoryMapper.toDTO(category);

        // Assert
        assertNotNull(dto);
        assertEquals(5, dto.getCategoryID());
        assertEquals("ExistingCat", dto.getName());
    }

    @Test
    void testToDTO_withNull() {
        // Act & Assert
        assertNull(CategoryMapper.toDTO(null));
    }

    @Test
    void testToModel_withValidDTO() {
        // Arrange
        CategoryDTO dto = new CategoryDTO(7, "DTOCat");

        // Act
        Category category = CategoryMapper.toModel(dto);

        // Assert
        assertNotNull(category);
        assertEquals(7, category.getCategoryID());
        assertEquals("DTOCat", category.getName());
    }

    @Test
    void testToModel_withNull() {
        // Act & Assert
        assertNull(CategoryMapper.toModel(null));
    }

    @Test
    void testToDTOList_withNullList() {
        // Act
        List<CategoryDTO> result = CategoryMapper.toDTOList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToDTOList_withValidCategories() {
        // Arrange
        List<Category> categories = List.of(
                new Category(1, "Cat1"),
                new Category(0, "Cat2")
        );

        // Act
        List<CategoryDTO> categoryDTOList = CategoryMapper.toDTOList(categories);

        // Assert
        assertEquals(2, categoryDTOList.size());
        assertEquals("Cat1", categoryDTOList.get(0).getName());
        assertEquals("Cat2", categoryDTOList.get(1).getName());
    }

    @Test
    void testToModelList_withNullList() {
        // Act
        List<Category> result = CategoryMapper.toModelList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToModelList_withValidDTOs() {
        // Arrange
        List<CategoryDTO> categoryDTOList = List.of(
                new CategoryDTO(3, "DTO1"),
                new CategoryDTO(0, "DTO2")
        );

        // Act
        List<Category> categories = CategoryMapper.toModelList(categoryDTOList);

        // Assert
        assertEquals(2, categories.size());
        assertEquals(3, categories.get(0).getCategoryID());
        assertEquals("DTO2", categories.get(1).getName());
    }
}
