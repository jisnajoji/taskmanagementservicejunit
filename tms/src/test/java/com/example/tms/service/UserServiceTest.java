package com.example.tms.service;

import com.example.tms.dao.UserRequest;
import com.example.tms.models.User;
import com.example.tms.repositories.UserRepository;
import com.example.tms.service.user.UserServiceImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class) // if this is used no need of openmocks
//intializes mocks w/o requiring openmock
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

//    public UserServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testCreateUser_Success() throws BadRequestException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        UserRequest user1 = new UserRequest();

        user1.setUsername("jisna");
        user1.setPassword("password");
        user1.setEmail("test@example.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        User createdUser = userService.createUser(user1);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @ParameterizedTest
    @CsvSource({
            "jisna1, jisna1, jisnamaria1@gmail.com",
            "jisna2, jisna2, jisnamaria2@gmail.com"
    })
    void testCreateUserWithValidData(String username, String password, String email) throws BadRequestException {
        UserRequest request = new UserRequest(username, password, email);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        User createdUser = userService.createUser(request);
        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(email, createdUser.getEmail());
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("jisna");
        userRequest.setPassword("password");
        userRequest.setEmail("email@email.com");
        when(userRepository.findByUsername("jisna")).thenReturn(new User());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("username already exists", exception.getMessage());
        verify(userRepository, never()).save(Mockito.any(User.class));
    }

    @ParameterizedTest
    @CsvSource({
            "'', password123, email@example.com", // Invalid username
            "validuser, '', email@example.com", // Invalid password
            "validuser, password123, ''", // Invalid email
    })
    void testCreateUserWithInvalidData(String username, String password, String email) {
        UserRequest request = new UserRequest(username, password, email);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.createUser(request));

        if (username == null || username.isBlank()) {
            assertEquals("Username cannot be null or empty", exception.getMessage());
        } else if (password == null || password.isBlank()) {
            assertEquals("Password cannot be null or empty", exception.getMessage());
        } else if (email == null || email.isBlank()) {
            assertEquals("Email cannot be null or empty", exception.getMessage());
        }
    }

    @Test
    void testCreateUser_MissingUsername() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(null);
        userRequest.setPassword("test");
        userRequest.setEmail("test@test.com");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("Username cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateUser_MissingPassword() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@test.com");
        userRequest.setPassword(null);
        userRequest.setUsername("username");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCreateUser_MissingEmail() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setEmail(null);
        userRequest.setPassword("password");

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.createUser(userRequest);
        });

        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void testFindUserByUsername_UserExists() {
        User mockUser = new User();
        mockUser.setUsername("jisna");
        mockUser.setEmail("jisnamaria@gmail.com");

        when(userRepository.findByUsername("jisna")).thenReturn(mockUser);
        User foundUser = userService.findUserByUsername("jisna");

        assertNotNull(foundUser);
        assertEquals("jisna", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("jisna");
    }

    @Test
    void testFindUserByUserName_UserNotFound() {
        when(userRepository.findByUsername("unknownUsr")).thenReturn(null);
        User foundUser = userService.findUserByUsername("unknownUsr");

        assertNull(foundUser);
        verify(userRepository, times(1)).findByUsername("unknownUsr");

    }

    @Test
    void testFindUserByUsername_NullOrEmpty() {
        User foundUserNull = userService.findUserByUsername(null);
        User foundUserEmpty = userService.findUserByUsername("");

        assertNull(foundUserNull);
        assertNull(foundUserEmpty);

//        verify(userRepository, never()).findByUsername(any());
//        verify(userRepository, never()).findByUsername("");
    }

}
