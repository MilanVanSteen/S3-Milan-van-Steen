package com.example.backend.Interfaces;

import com.example.backend.DTOs.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterface extends CrudRepository<UserDTO, String> {
}
