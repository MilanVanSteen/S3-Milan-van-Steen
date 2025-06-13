package com.example.backend.interfaces;

import com.example.backend.dtos.EventDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventInterface extends CrudRepository<EventDTO, Integer> {
    void deleteAllByUserDTO_UserID(int userID);
}
