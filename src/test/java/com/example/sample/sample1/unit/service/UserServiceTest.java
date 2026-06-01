package com.example.sample.sample1.unit.service;


import com.example.sample.sample1.config.JwtTokenProvider;
import com.example.sample.sample1.dto.request.LoginRequest;
import com.example.sample.sample1.dto.request.RegisterRequest;
import com.example.sample.sample1.dto.response.LoginResponse;
import com.example.sample.sample1.model.User;
import com.example.sample.sample1.repository.UserRepository;
import com.example.sample.sample1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("User Service - Unit Tests")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp(){
        testUser = new User();
        testUser.setId(1L);
        testUser.setUserName("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encoded_password");
        testUser.setRole("USER");

        registerRequest = new RegisterRequest();
        registerRequest.setUserName("testUser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("test@example.com");

        loginRequest = new LoginRequest();
        loginRequest.setUserName("testUser");
        loginRequest.setPassword("password123");
    }

    @Test
    @DisplayName("Should register new user Successfully")
    void testRegisterSuccess() throws Exception{
        //Arrange
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        //Act
        User result = userService.register(registerRequest);

        //Assert
        assertNotNull(result);
        assertEquals("testUser",result.getUserName());
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    @DisplayName("Should throw exception for duplicate username")
    void testRegisterUserNameExists(){
        //Arrange
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        //Act & Assert
        assertThrows(Exception.class, () -> userService.register(registerRequest));
    }

    @Test
    @DisplayName("Should throw exception for Duplicate Email")
    void testRegisterEmailExists(){
        //Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        //Act & Assert
        assertThrows(Exception.class, () -> userService.register(registerRequest));
    }

    @Test
    @DisplayName("Should login user successfully")
    void testLoginSuccess() throws Exception{
        //Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(anyString(), anyString())).thenReturn("jwt_token");

        //Act
        LoginResponse response = userService.login(loginRequest);

        //Assert
        assertNotNull(response);
        assertEquals("testUser",response.getUsername());
        assertEquals("USER", response.getRole());
        assertEquals("jwt_token", response.getToken());
    }

    @Test
    @DisplayName("Should throw Exception when userName doesn't exist")
    void testLoginUserNameInvalid(){
        //Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(null);

        //Act & Assert
        assertThrows(Exception.class, () -> userService.login(loginRequest));
    }

    @Test
    @DisplayName("Should throw exception when user entered password doesn't match")
    void testLoginPasswordIncorrect(){
        //Arrange
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.ofNullable(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        //Act & Assert
        assertThrows(Exception.class, () -> userService.login(loginRequest));
    }
}
