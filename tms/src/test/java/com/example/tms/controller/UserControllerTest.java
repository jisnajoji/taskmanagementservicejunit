package com.example.tms.controller;

import com.example.tms.dao.UserRequest;
import com.example.tms.models.User;
import com.example.tms.service.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private  UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }



    @Test
    public void testRegisterUser_MissingUsername() throws Exception {
        UserRequest userRequest = new UserRequest(null, "password", "test@example.com");

        String jsonString = "{\"username\":null,\"password\":\"password\",\"email\":\"test@example.com\"}";


        when(userService.createUser(Mockito.any()))
                .thenThrow(new BadRequestException("Username cannot be null or empty"));

        mockMvc.perform(post("http://localhost:8080/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.status").value("Bad Request"))
                        .andExpect(jsonPath("$.error").value("Username cannot be null or empty"));

        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    public void tet() throws Exception{
        UserRequest user = new UserRequest();
        user.setUsername("hi");
        when(userService.createUser(user))
                .thenThrow(new BadRequestException("Username cannot be null or empty"));
        assertThrows(BadRequestException.class, () -> {userController.registerUser(user);
        });
    }
    @Test
    public void testRegisteredUser_success() throws Exception {
        UserRequest userRequest = new UserRequest("test", "", "test@example.com");
        User userResponse = new User(1L, "test", "", "test@example.com");

        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("http://localhost:8080/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("test"))
                .andExpect(jsonPath("$.password").value(""))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws Exception {
        UserRequest userRequest = new UserRequest("jisna", "password", "test@example.com");

        String jsonString = "{\"username\":\"jisna\",\"password\":\"password\",\"email\":\"test@example.com\"}";

        when(userService.createUser(any(UserRequest.class)))
                .thenThrow(new BadRequestException("username already exists"));

        mockMvc.perform(post("http://localhost:8080/api/users/register")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("Bad Request"))
                .andExpect(jsonPath("$.error").value("username already exists"));

        verify(userService, times(1)).createUser(any(UserRequest.class));
    }


}
