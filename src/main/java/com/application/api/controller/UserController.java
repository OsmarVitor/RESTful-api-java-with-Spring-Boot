package com.application.api.controller;

import java.util.List;

import com.application.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.application.api.dto.UserDTO;
import com.application.api.models.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
 
    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> listUsers() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = service.save(userDTO);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/users/{identifier}")
                    .buildAndExpand(user.getIdentifier()).toUri())
                .build();
    }

    @GetMapping(value = "/{identifier}")
    public ResponseEntity<UserDTO> getUserByIdentifier(
            @PathVariable(name = "identifier", required = true) String identifier) {
        return ResponseEntity.ok(service.findByIdentifier(identifier));
    }

    @PutMapping("/{identifier}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(value = "identifier") String identifier,
            @RequestBody UserDTO userDTO) {
        service.updateUser(userDTO, identifier);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{identifier}")
    public ResponseEntity<Void> deleteUser(@PathVariable String identifier) {
        service.deleteUser(identifier);
        return ResponseEntity.noContent().build();
    }

}
