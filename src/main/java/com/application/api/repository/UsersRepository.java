package com.application.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.api.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {

    Optional<User> findByName(String name);

    Optional<User> findByIdentifier(String identifier);
   
}
