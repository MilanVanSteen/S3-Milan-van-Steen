package com.example.backend.interfaces;

import com.example.backend.dtos.CategoryDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryInterface extends CrudRepository<CategoryDTO, Integer> {
}
