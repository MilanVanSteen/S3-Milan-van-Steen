package com.example.backend.Interfaces;

import com.example.backend.DTOs.CategoryDTO;
import com.example.backend.DTOs.EventCategoryDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCategoryInterface extends CrudRepository<EventCategoryDTO, Integer> {
    @Query("SELECT new CategoryDTO(c.CategoryID, c.Name) FROM EventCategoryDTO ec, CategoryDTO c where ec.CategoryDTO.CategoryID = c.CategoryID and ec.EventDTO.EventID = ?1")
    List<CategoryDTO> findCategoriesByEventID(int eventID);
}
