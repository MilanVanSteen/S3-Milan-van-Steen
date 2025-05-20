package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryDTO {
    @Id
    private int CategoryID;
    private String Name;

    public CategoryDTO(int categoryID, String name) {
        this.CategoryID = categoryID;
        this.Name = name;
    }

    public CategoryDTO() {

    }
}
