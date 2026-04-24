package com.example.sample.sample1.dto.request;

import lombok.Data;
import org.jspecify.annotations.Nullable;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
