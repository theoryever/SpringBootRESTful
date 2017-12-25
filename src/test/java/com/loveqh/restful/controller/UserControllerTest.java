package com.loveqh.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(classes = UserController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TestRestTemplate restTemplate;

    private MockMvc mvc;

    @BeforeClass
    public void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testUserController() throws Exception {
//        RequestBuilder request = null;
//        request = get("/v1/users");
//        mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string("[]"));
    }

}
