package com.application.api.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotNull
    private String name;

    @JsonProperty("birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    public String identifier;
    
    public UserDTO(String name, LocalDate birthDate, String identifier) {
        this.name = name;
        this.birthDate = birthDate;
        this.identifier = identifier;
    }

    public UserDTO() {}
    
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "UserDTO [name=" + name + ", birthDate=" + birthDate
                + ", identifier=" + identifier + "]";
    }

}
