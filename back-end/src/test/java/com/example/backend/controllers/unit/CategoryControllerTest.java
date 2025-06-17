package com.example.backend.controllers.unit;

import com.example.backend.containers.CategoryContainer;
import com.example.backend.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryContainer categoryContainer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllCategories_withResults_returnsOk() throws Exception {
        Category category = new Category();
        category.setCategoryID(1);
        category.setName("Test Category");

        when(categoryContainer.getAllCategories()).thenReturn(List.of(category));

        mockMvc.perform(get("/category/getAllCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryID").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Category"));
    }

    @Test
    void testGetAllCategories_noResults_returnsNoContent() throws Exception {
        when(categoryContainer.getAllCategories()).thenReturn(List.of());

        mockMvc.perform(get("/category/getAllCategories"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCategoryById_exists_returnsOk() throws Exception {
        Category category = new Category();
        category.setCategoryID(1);
        category.setName("Sample Category");

        when(categoryContainer.getCategoryById(1)).thenReturn(category);

        mockMvc.perform(get("/category/getCategoryById").param("categoryID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Category"));
    }

    @Test
    void testGetCategoryById_notFound_returnsNotFound() throws Exception {
        when(categoryContainer.getCategoryById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/category/getCategoryById").param("categoryID", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCategory_returnsCreatedCategory() throws Exception {
        Category category = new Category();
        category.setCategoryID(5);
        category.setName("Created Category");

        when(categoryContainer.createCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/category/createCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryID").value(5))
                .andExpect(jsonPath("$.name").value("Created Category"));
    }

    @Test
    void testDeleteCategory_success_returnsOk() throws Exception {
        when(categoryContainer.deleteCategory(1)).thenReturn(true);

        mockMvc.perform(delete("/category/deleteCategory").param("categoryID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted category"));
    }

    @Test
    void testDeleteCategory_notFound_returnsNoContent() throws Exception {
        when(categoryContainer.deleteCategory(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/category/deleteCategory").param("categoryID", "123"))
                .andExpect(status().isNoContent());
    }
}