package com.application.api.models;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.application.api.dto.UserDTO;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @NotNull(message = "NAME cannot be null")
    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "BIRTHDATE cannot be null")
    private LocalDate birthDate;

    @Column(name = "identifier", unique = true)
    @NotNull(message = "IDENTIFIER cannot be null")
    public String identifier;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", name = "admin")
    public boolean admin;

    @SuppressWarnings("unused")
    private User() {
    }

    public User(String name, LocalDate birthDate, String identifier) {
        this.name = name;
        this.birthDate = birthDate;
        this.identifier = identifier;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Users [uuid=" + uuid + ", name=" + name + ", birth_date="
                + birthDate + "]";
    }

    public static UserDTO valueOf(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setBirthDate(entity.getBirthDate());
        userDTO.setIdentifier(entity.getIdentifier());
        userDTO.setName(entity.getName());
        return userDTO;
    }

}