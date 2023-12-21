package com.anton.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void returnNewCustomerIfValidRequest() throws Exception {
        Object randomObj = new Object() {
            public final String name = "testName";
            public final String email = "testName";
            public final String phoneNumber = "1818181818";
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(randomObj);

        this.mockMvc.perform(post("/api/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                .andExpect(status().is(201))
                .andReturn();

        this.mockMvc.perform(get("/api/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("mobileNumber", "1818181818"))
                .andExpect(status().is(200))
                //.andExpect(content().json(""))
                .andReturn();
    }

    @Test
    void returnBadRequestWhenInvalidPhoneNUmberPassed() throws Exception {

        this.mockMvc.perform(get("/api/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("mobileNumber", "1818181818"))
                .andExpect(status().is(404))
                .andReturn();
    }

}
