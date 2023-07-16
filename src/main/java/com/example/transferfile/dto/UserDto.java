package com.example.transferfile.dto;

import com.example.transferfile.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private boolean loggedIn;
    private boolean active;
}
