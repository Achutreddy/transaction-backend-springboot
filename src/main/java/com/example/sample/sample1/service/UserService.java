package com.example.sample.sample1.service;

import com.example.sample.sample1.config.JwtTokenProvider;
import com.example.sample.sample1.dto.request.LoginRequest;
import com.example.sample.sample1.dto.request.RegisterRequest;
import com.example.sample.sample1.dto.response.LoginResponse;
import com.example.sample.sample1.model.User;
import com.example.sample.sample1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User register(RegisterRequest req) throws Exception{
        if(userRepository.existsByUserName(req.getUserName())){
            throw new Exception("Username already exists!");
        }
        if(userRepository.existsByEmail(req.getEmail())){
            throw new Exception("Email already exists!");
        }
        User u = new User();
        u.setUserName(req.getUserName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole("USER");
        return userRepository.save(u);
    }

    public LoginResponse login(LoginRequest req) throws Exception{
        User u = userRepository.findByUserName(req.getUserName())
                .orElseThrow(() -> new Exception("No User"));
        if(!(passwordEncoder.matches(req.getPassword(), u.getPassword())))
            throw new Exception("Bad Password!");
        String token = jwtTokenProvider.generateToken(u.getUserName(),u.getRole());
        return new LoginResponse(u.getUserName(), token, u.getRole());
    }
}
