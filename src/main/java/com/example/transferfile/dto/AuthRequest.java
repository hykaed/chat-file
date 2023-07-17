package com.example.transferfile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

    @NotNull(message = "This can't be null")
    private String username;

    @NotNull(message = "This can't be null")
    private String password;
}
