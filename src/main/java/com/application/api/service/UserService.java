package com.application.api.service;

import java.util.List;

import com.application.api.dto.UserDTO;
import com.application.api.models.User;

public interface UserService {
    List<UserDTO> findAll();
    
    User save(UserDTO userDTO);
    
    UserDTO findByIdentifier(String identifier);
    
    User updateUser(UserDTO userDTO, String identifier);
    
    void deleteUser(String identifier);
}
