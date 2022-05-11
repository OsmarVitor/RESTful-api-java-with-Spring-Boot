package com.application.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.api.dto.UserDTO;
import com.application.api.exception.UserNotFoundException;
import com.application.api.models.User;
import com.application.api.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> usersDTO = new ArrayList<>();

        userRepository.findAll().stream().forEach(user -> {
            usersDTO.add(User.valueOf(user));
        });

        return usersDTO;
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User(userDTO.getName(), userDTO.getBirthDate(),
                userDTO.getIdentifier());
        return userRepository.save(user);
    }

    @Override
    public UserDTO findByIdentifier(String identifier) {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new UserNotFoundException("User com o identificador ["
                        + identifier + "] não cadastrado no sitema"));
        UserDTO userDTO = User.valueOf(user);
        return userDTO;
    }

    @Override
    public User updateUser(UserDTO userDTO, String identifier) {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new UserNotFoundException("User com o identificador ["
                        + identifier + "] não cadastrado no sitema"));
        user.setName(userDTO.getName());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String identifier) {
        User user = userRepository.findByIdentifier(identifier).orElseThrow(
                () -> new UserNotFoundException("User com o identificador ["
                        + identifier + "] não cadastrado no sistema"));
        userRepository.delete(user);
    }

}
