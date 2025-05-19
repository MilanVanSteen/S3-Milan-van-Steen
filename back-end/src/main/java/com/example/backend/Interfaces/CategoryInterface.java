package com.example.backend.Interfaces;

import com.example.backend.DTOs.CategoryDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInterface extends CrudRepository<CategoryDTO, Integer> {
}
