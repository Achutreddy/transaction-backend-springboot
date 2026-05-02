package com.example.sample.sample1.unit.service;


import com.example.sample.sample1.config.JwtTokenProvider;
import com.example.sample.sample1.dto.request.LoginRequest;
import com.example.sample.sample1.dto.request.RegisterRequest;
import com.example.sample.sample1.model.User;
import com.example.sample.sample1.repository.UserRepository;
import com.example.sample.sample1.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("User Service - Unit Tests")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

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
}
