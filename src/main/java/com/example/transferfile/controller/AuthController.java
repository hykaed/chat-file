package com.example.transferfile.controller;

import com.example.transferfile.dto.AuthRequest;
import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.UserException;
import com.example.transferfile.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) throws AuthException, UserException {
        userService.registerUser(request);
        return userService.loginUser(new AuthRequest(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) throws AuthException, UserException {
        return userService.loginUser(authRequest);
    }

    @GetMapping("/logout")
    public void logout() throws UserException {
        userService.logout();
    }
}
