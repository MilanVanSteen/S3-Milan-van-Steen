package com.example.backend.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
public class CategoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    private String name;

    public CategoryDTO(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO() {}
}
