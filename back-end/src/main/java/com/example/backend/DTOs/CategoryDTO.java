package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryDTO {
    @Id
    private int categoryID;
    private String name;

    public CategoryDTO(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public CategoryDTO() {

    }
}
