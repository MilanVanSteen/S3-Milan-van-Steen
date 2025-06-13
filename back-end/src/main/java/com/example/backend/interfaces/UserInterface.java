package com.example.backend.interfaces;

import com.example.backend.dtos.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterface extends CrudRepository<UserDTO, Integer> {
}
