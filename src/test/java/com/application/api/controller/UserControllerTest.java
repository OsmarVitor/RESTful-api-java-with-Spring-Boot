package com.application.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.application.api.dto.UserDTO;
import com.application.api.service.UserService;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/scripts/sql/users/delete_all_users.sql")
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService service;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldSaveUser() throws Exception {
        String user = "{\n" + "        \"name\": \"User Test\",\n"
                + "        \"birth_date\": \"1997-11-07\",\n"
                + "        \"identifier\": \"44455182832\"\n" + "}";

        mockMvc
                .perform(post("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(status().isCreated());

        mockMvc
                .perform(get("/api/users/" + "44455182832")
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("User Test"))
                .andExpect(jsonPath("birth_date").value("1997-11-07"))
                .andExpect(jsonPath("identifier").value("44455182832"));
    }

    @Test
    public void shouldGetUserByIdentifier() throws Exception {
        service.save(new UserDTO("User Test", LocalDate.of(1997, 07, 07),
                "00000000000"));

        mockMvc
                .perform(get("/api/users/" + "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("User Test"))
                .andExpect(jsonPath("birth_date").value("1997-07-07"))
                .andExpect(jsonPath("identifier").value("00000000000"));
    }

    @Test
    public void shouldUpdateUser() throws Exception{
        service.save(new UserDTO("User Test", LocalDate.of(1997, 07, 07),
                "00000000000"));

        mockMvc
                .perform(get("/api/users/" + "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("User Test"));

        mockMvc.perform(put("/api/users/" + "00000000000")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{\n"
                        + "        \"name\": \"User Altered\"\n"
                        + "}")).andExpect(status().isNoContent());

        mockMvc
                .perform(get("/api/users/" + "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("User Altered"));

    }

    @Test
    public void shouldNotUpdateNonexistentUser() throws Exception{
        mockMvc.perform(put("/api/users/" + "00000000000")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{\n"
                                + "        \"name\": \"Teste Altered\"\n"
                                + "}")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAUser() throws Exception{
        service.save(new UserDTO("User Test", LocalDate.of(1997, 07, 07),
                "00000000000"));

        mockMvc.perform(delete("/api/users/" + "00000000000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        String body = mockMvc.perform(get("/api/users/" + "00000000000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn().getResolvedException().getMessage();

        Assert.assertEquals("User com o identificador [00000000000] não cadastrado no sitema", body);
    }


    @Test
    public void shouldNotDeleteNonexistentUser() throws Exception{
        mockMvc.perform(delete("/api/users/" + "00000000000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldListAllUsers() throws Exception {
        service.save(new UserDTO("User Test One", LocalDate.of(1997, 07, 07),
                "11111111111"));
        service.save(new UserDTO("User Test Two", LocalDate.of(1972, 11, 01),
                "22222222222"));
        service.save(new UserDTO("User Test Three", LocalDate.of(1980, 03, 02),
                "33333333333"));

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("User Test One"))
                .andExpect(jsonPath("$.[0].birth_date").value("1997-07-07"))
                .andExpect(jsonPath("$.[0].identifier").value("11111111111"))
                .andExpect(jsonPath("$.[1].name").value("User Test Two"))
                .andExpect(jsonPath("$.[1].birth_date").value("1972-11-01"))
                .andExpect(jsonPath("$.[1].identifier").value("22222222222"))
                .andExpect(jsonPath("$.[2].name").value("User Test Three"))
                .andExpect(jsonPath("$.[2].birth_date").value("1980-03-02"))
                .andExpect(jsonPath("$.[2].identifier").value("33333333333"));

    }
}
