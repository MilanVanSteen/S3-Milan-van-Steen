package com.example.backend.controllers.integration;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.interfaces.CategoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryInterface categoryRepository;

    private CategoryDTO testCategory;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        testCategory = new CategoryDTO();
        testCategory.setName("Test Category");
        categoryRepository.save(testCategory);
    }

    @Test
    void testGetAllCategories_returnsFromDatabase() throws Exception {
        mockMvc.perform(get("/category/getAllCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(testCategory.getName()))
                .andExpect(jsonPath("$[0].categoryID").isNumber());
    }

    @Test
    void testGetCategoryById_returnsCorrectCategory() throws Exception {
        mockMvc.perform(get("/category/getCategoryById")
                        .param("categoryID", String.valueOf(testCategory.getCategoryID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryID").value(testCategory.getCategoryID()))
                .andExpect(jsonPath("$.name").value(testCategory.getName()));
    }

    @Test
    void testCreateCategory_createsAndReturnsCategory() throws Exception {
        String json = """
        {
            "name": "New Category"
        }
        """;

        mockMvc.perform(post("/category/createCategory")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryID").isNumber())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testDeleteCategory_deletesSuccessfully() throws Exception {
        mockMvc.perform(delete("/category/deleteCategory")
                        .param("categoryID", String.valueOf(testCategory.getCategoryID())))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted category"));

        // Verify it's gone
        mockMvc.perform(get("/category/getCategoryById")
                        .param("categoryID", String.valueOf(testCategory.getCategoryID())))
                .andExpect(status().isNotFound());
    }
}