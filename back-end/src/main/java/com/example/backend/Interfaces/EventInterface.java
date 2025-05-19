package com.example.backend.Interfaces;

import com.example.backend.DTOs.EventDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventInterface extends CrudRepository<EventDTO, String> {
}
